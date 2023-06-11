package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.team.Team;
import net.toujoustudios.hyperspecies.data.team.TeamStatus;
import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamUI implements Listener {

    private static final HashMap<Integer, Inventory> inventories = new HashMap<>();
    private static final ArrayList<UUID> creatingTeamPlayers = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equals("Team Management")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.ENDER_EYE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, TeamPage.BROWSE.getIndex());
            } else if(material == Material.NETHER_STAR) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                creatingTeamPlayers.add(player.getUniqueId());
                player.sendMessage(Config.MESSAGE_PREFIX + " §7Please type in the §bname§7 of the new team§8.");
                player.closeInventory();
            } else if(material == Material.BARRIER) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
            }

        } else if(event.getView().getTitle().equals("Browse Teams")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.PLAYER_HEAD) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player);
            }

        }

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if(getCreatingTeamPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
            String name = event.getMessage();
            Pattern pattern = Pattern.compile("^[ A-Za-z]+$");
            Matcher matcher = pattern.matcher(name);
            if(!matcher.matches()) {
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe team name can only contain letters and spaces§8.");
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
            player.sendMessage(Config.MESSAGE_PREFIX + " §7The team §b" + name + "§7 has been created§8.");
            Team.createTeam(name, "§f", player.getUniqueId(), TeamStatus.INVITE, null);
            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.setTeam(Team.getTeam(name));
            getCreatingTeamPlayers().remove(player.getUniqueId());
        }

    }

    public static void initialize() {

        // MAIN

        Inventory pageMain = Bukkit.createInventory(null, 9*3, "Team Management");
        for(int i = 0; i < pageMain.getSize(); i++) pageMain.setItem(i, ItemList.FILLER);
        pageMain.setItem(10, ItemList.CANCEL);
        pageMain.setItem(13, ItemList.TEAM_BROWSE);
        pageMain.setItem(16, ItemList.TEAM_CREATE);

        Inventory pageBrowse = Bukkit.createInventory(null, 9*4, "Browse Teams");
        for(int i = 27; i < pageBrowse.getSize(); i++) pageBrowse.setItem(i, ItemList.FILLER);
        pageBrowse.setItem(31, ItemList.PREVIOUS);

        AtomicInteger i = new AtomicInteger();
        Team.getTeams().forEach((name, team) -> {

            StringBuilder memberBuilder = new StringBuilder();
            memberBuilder.append("§7Members:\n");

            if(team.getMembers().size() > 0) {
                team.getMembers().forEach(member -> {
                    memberBuilder.append("§7- §b").append(Bukkit.getOfflinePlayer(member).getName());
                });
            } else memberBuilder.append("§8None");

            ItemStack item = new ItemStack(Material.ENDER_EYE);
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName(team.getColor() + team.getName());
            itemMeta.setLore(List.of(
                    "§7Status: " + team.getStatus().getColor() + team.getStatus().getName(),
                    "§7Owner: §b" + Bukkit.getOfflinePlayer(team.getOwner()).getName(),
                    "§r",
                    memberBuilder.toString()
            ));
            item.setItemMeta(itemMeta);
            pageBrowse.setItem(i.get(), item);
            i.getAndIncrement();
        });

        inventories.put(TeamPage.MAIN.getIndex(), pageMain);
        inventories.put(TeamPage.BROWSE.getIndex(), pageBrowse);

    }

    public static void refresh() {
        initialize();
    }

    public static void openInventory(Player player) {
        openInventory(player, 0);
    }

    public static void openInventory(Player player, int page) {
        if(inventories.get(page) == null) return;
        player.openInventory(inventories.get(page));
    }

    public static ArrayList<UUID> getCreatingTeamPlayers() {
        return creatingTeamPlayers;
    }

}
