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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamUI implements Listener {

    private static final HashMap<Integer, Inventory> inventories = new HashMap<>();
    private static final ArrayList<UUID> creatingTeamPlayers = new ArrayList<>();

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

        Player player = (Player) event.getPlayer();

        if(event.getView().getTitle().equals("Team: ")) {

            PlayerManager playerManager = PlayerManager.getPlayer(player);


        }

    }

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
            if(event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();

            if(material == Material.PLAYER_HEAD) {

                //stuff

            } else if(material == Material.BARRIER) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player);

            } else if(material == Material.ENDER_EYE) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

            }

        } else if(event.getView().getTitle().startsWith("Team: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.BARRIER) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
            } else if(material == Material.REDSTONE) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                PlayerManager playerManager = PlayerManager.getPlayer(player);

                if(playerManager.getTeam().getOwner().equals(player.getUniqueId())) {
                    openInventory(player, TeamPage.SETTINGS_ADMIN.getIndex());
                } else {
                    openInventory(player, TeamPage.SETTINGS.getIndex());
                }

            }

        } else if(event.getView().getTitle().equals("Team Settings")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.PLAYER_HEAD) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();

            } else if(material == Material.PUFFERFISH) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, TeamPage.LEAVE_CONFIRM.getIndex());

            }

        } else if(event.getView().getTitle().equals("Leave Team")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.BARRIER) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();

            } else if(material == Material.PUFFERFISH) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You left your team and are all alone now§8.");

                PlayerManager playerManager = PlayerManager.getPlayer(player);
                playerManager.getTeam().setOwner(null);
                Objects.requireNonNull(Team.getTeam(playerManager.getTeam().getName())).setOwner(null);
                playerManager.setTeam(null);
                TeamUI.refresh();

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
            Team.createTeam(name, null, "§e", player.getUniqueId(), TeamStatus.INVITE, null);
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

        // BROWSE

        Inventory pageBrowse = Bukkit.createInventory(null, 9*5, "Browse Teams");
        for(int i = 0; i < 9; i++) pageBrowse.setItem(i, ItemList.FILLER);
        for(int i = 36; i < pageBrowse.getSize(); i++) pageBrowse.setItem(i, ItemList.FILLER);
        pageBrowse.setItem(36, ItemList.PREVIOUS);
        pageBrowse.setItem(40, ItemList.CANCEL);
        pageBrowse.setItem(44, ItemList.NEXT);

        AtomicInteger num = new AtomicInteger();
        Team.getTeams().forEach((name, team) -> {

            if(team.getOwner() != null) {
                ArrayList<String> lore = new ArrayList<>();

                if(team.getDescription() != null && team.getDescription().length() > 0) {
                    String[] descriptionLines = team.getDescription().split(" ");
                    for (String descriptionLine : descriptionLines) {
                        lore.add("§7" + descriptionLine);
                    }
                } else {
                    lore.add("§8No Description");
                }

                lore.add("§r");
                lore.add("§7Status: " + team.getStatus().getColor() + team.getStatus().getName());
                lore.add("§7Owner: §b" + Bukkit.getOfflinePlayer(team.getOwner()).getName());
                lore.add("§r");
                lore.add("§7Members:");

                if(team.getMembers().size() > 0) {
                    team.getMembers().forEach(member -> lore.add("§8- §b" + Bukkit.getOfflinePlayer(member).getName()));
                } else lore.add("§8No Members");

                ItemStack item = new ItemStack(Material.ENDER_EYE);
                ItemMeta itemMeta = item.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(team.getColor() + team.getName());
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                pageBrowse.setItem(num.get()+9, item);
                num.getAndIncrement();
            }

        });

        // SETTINGS

        Inventory pageSettings = Bukkit.createInventory(null, 9*3, "Team Settings");
        for(int i = 0; i < pageSettings.getSize(); i++) pageSettings.setItem(i, ItemList.FILLER);
        pageSettings.setItem(10, ItemList.PREVIOUS);
        pageSettings.setItem(16, ItemList.TEAM_LEAVE);

        // LEAVE CONFIRM

        Inventory pageLeaveConfirm = Bukkit.createInventory(null, 9*3, "Leave Team");
        for(int i = 0; i < pageLeaveConfirm.getSize(); i++) pageLeaveConfirm.setItem(i, ItemList.FILLER);
        pageLeaveConfirm.setItem(16, ItemList.CANCEL);
        pageLeaveConfirm.setItem(10, ItemList.TEAM_LEAVE_CONFIRM);

        inventories.put(TeamPage.MAIN.getIndex(), pageMain);
        inventories.put(TeamPage.BROWSE.getIndex(), pageBrowse);
        inventories.put(TeamPage.SETTINGS.getIndex(), pageSettings);
        inventories.put(TeamPage.SETTINGS_ADMIN.getIndex(), pageSettings);
        inventories.put(TeamPage.LEAVE_CONFIRM.getIndex(), pageLeaveConfirm);

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
