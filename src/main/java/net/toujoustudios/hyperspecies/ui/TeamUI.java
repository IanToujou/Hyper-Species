package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.team.Team;
import net.toujoustudios.hyperspecies.data.team.TeamStatus;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamUI implements Listener {

    private static final HashMap<Integer, Inventory> inventories = new HashMap<>();
    private static final HashMap<Integer, Inventory> teamBrowsePages = new HashMap<>();
    private static final ArrayList<UUID> creatingTeamPlayers = new ArrayList<>();
    private static final ArrayList<UUID> changingNamePlayers = new ArrayList<>();
    private static final ArrayList<UUID> changingDescriptionPlayers = new ArrayList<>();
    private static final HashMap<UUID, String> teamJoinRequests = new HashMap<>();

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equals("Team Management")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.ENDER_EYE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.openInventory(teamBrowsePages.get(0));
            } else if(material == Material.NETHER_STAR) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                creatingTeamPlayers.add(player.getUniqueId());
                player.sendMessage(Config.MESSAGE_PREFIX + " §7Please type in the §bname§7 of the new team§8. §7Type §bcancel§7 to cancel§8.");
                player.closeInventory();
            } else if(material == Material.BARRIER) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
            }

        } else if(event.getView().getTitle().startsWith("Browse Teams: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if(event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            int page = Integer.parseInt(event.getView().getTitle().split(" ")[3]);
            int pageIndex = page-1;

            if(material == Material.PLAYER_HEAD) {

                if(name.equals("§eBack")) {

                    if(teamBrowsePages.get(pageIndex-1) == null) return;

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                    player.openInventory(teamBrowsePages.get(pageIndex-1));

                } else if(name.equals("§eNext")) {

                    if(teamBrowsePages.get(pageIndex+1) == null) return;

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                    player.openInventory(teamBrowsePages.get(pageIndex+1));

                }

            } else if(material == Material.BARRIER) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player);

            } else if(material == Material.ENDER_EYE) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();

                Team team = Team.getTeam(name.substring(2));

                if(team == null || team.getOwner() == null) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                    player.sendMessage(Config.MESSAGE_PREFIX + " §cYou are unable to join this team§8.");
                    return;
                }

                if(team.getStatus() == TeamStatus.OPEN) {

                    PlayerManager playerManager = PlayerManager.getPlayer(player);
                    playerManager.setTeam(team.getName());
                    team.addMember(player.getUniqueId());

                } else if(team.getStatus() == TeamStatus.INVITE) {

                    Player target = Bukkit.getPlayer(team.getOwner());

                    if(target == null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                        player.sendMessage(Config.MESSAGE_PREFIX + " §cThe owner of the team is not online§8.");
                        return;
                    }

                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                    player.sendMessage(Config.MESSAGE_PREFIX + " §7You sent a join request to the team§8.");
                    target.sendMessage(Config.MESSAGE_PREFIX + " §e" + player.getName() + "§7 wants to join your team§8.");
                    target.sendMessage(Config.MESSAGE_PREFIX + " §7Please use §b/team accept " + player.getName() + "§7 to §aaccept§8.");
                    target.sendMessage(Config.MESSAGE_PREFIX + " §7Or use §b/team deny " + player.getName() + "§7 to §cdeny§8.");
                    teamJoinRequests.put(player.getUniqueId(), team.getName());

                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                    player.sendMessage(Config.MESSAGE_PREFIX + " §cThis team is closed and private§8.");
                }

            }

        } else if(event.getView().getTitle().startsWith("Team: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if(event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();

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

            } else if(material == Material.PLAYER_HEAD) {

                PlayerManager playerManager = PlayerManager.getPlayer(player);

                if(!playerManager.getTeam().getOwner().equals(player.getUniqueId())) return;

                OfflinePlayer target = Bukkit.getOfflinePlayer(name.substring(2));

                if(target.getUniqueId().equals(player.getUniqueId())) return;

                Inventory inventory = Bukkit.createInventory(null, 3*9, "Team Player: " + target.getName());
                for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemListUI.FILLER);
                inventory.setItem(10, ItemListUI.PREVIOUS);

                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                assert skullMeta != null;
                skullMeta.setOwningPlayer(target);
                skullMeta.setDisplayName("§e" + target.getName());
                skull.setItemMeta(skullMeta);

                inventory.setItem(12, skull);
                inventory.setItem(14, ItemListUI.TEAM_KICK_PLAYER);
                inventory.setItem(16, ItemListUI.TEAM_PROMOTE_PLAYER);

                player.openInventory(inventory);

            }

        } else if(event.getView().getTitle().startsWith("Team Player: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            OfflinePlayer target = Bukkit.getOfflinePlayer(event.getView().getTitle().split(" ")[2]);
            PlayerManager playerManager = PlayerManager.getPlayer(player);
            PlayerManager targetManager = PlayerManager.getPlayer(target.getUniqueId());

            if(material == Material.PLAYER_HEAD) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();

            } else if(material == Material.FIRE_CHARGE) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cYou kicked §e" + target.getName() + " §cout of the team§8.");
                playerManager.getTeam().removeMember(target.getUniqueId());
                targetManager.setTeam(null);
                player.closeInventory();

            } else if(material == Material.MAGMA_CREAM) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You promoted §e" + target.getName() + " §7to be the owner§8.");
                playerManager.getTeam().setOwner(target.getUniqueId());
                playerManager.getTeam().removeMember(target.getUniqueId());
                playerManager.getTeam().addMember(player.getUniqueId());
                player.closeInventory();

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

            } else if(material == Material.NAME_TAG) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_PREFIX + " §7Please type in a new team name§8. §7Type §bcancel§7 to cancel§8.");
                changingNamePlayers.add(player.getUniqueId());

            } else if(material == Material.MINECART) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_PREFIX + " §7Please type in a new team description§8. §7Type §bcancel§7 to cancel§8.");
                changingDescriptionPlayers.add(player.getUniqueId());

            } else if(material == Material.PURPLE_DYE) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, TeamPage.SETTINGS_STATUS.getIndex());

            }

        } else if(event.getView().getTitle().equals("Change Status")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            PlayerManager playerManager = PlayerManager.getPlayer(player);

            if(material == Material.PLAYER_HEAD) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, TeamPage.SETTINGS_ADMIN.getIndex());

            } else if(material == Material.GRAY_DYE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You changed the status to§8: §cClosed§8.");
                playerManager.getTeam().setStatus(TeamStatus.CLOSED);
                player.closeInventory();
            } else if(material == Material.PURPLE_DYE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You changed the status to§8: §dInvite§8.");
                playerManager.getTeam().setStatus(TeamStatus.INVITE);
                player.closeInventory();
            } else if(material == Material.LIME_DYE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You changed the status to§8: §aOpen§8.");
                playerManager.getTeam().setStatus(TeamStatus.OPEN);
                player.closeInventory();
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

                if(playerManager.getTeam().getOwner().toString().equals(player.getUniqueId().toString())) {

                    playerManager.getTeam().setOwner(null);

                    if(playerManager.getTeam().getMembers().size() > 0) {
                        int random = new Random().nextInt(playerManager.getTeam().getMembers().size());
                        playerManager.getTeam().setOwner(playerManager.getTeam().getMembers().get(random));
                        playerManager.getTeam().getMembers().remove(random);
                    }

                } else {
                    playerManager.getTeam().removeMember(player.getUniqueId());
                }

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
            if(name.equalsIgnoreCase("cancel")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cTeam creation has been cancelled§8.");
                creatingTeamPlayers.remove(player.getUniqueId());
                return;
            }
            if(!matcher.matches()) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe team name can only contain letters and spaces§8.");
                return;
            }
            if(Team.getTeam(name) != null) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cA team with this name already exists§8.");
                return;
            }
            if(name.length() > 30) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe name cannot be longer than 30 characters§8.");
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
            player.sendMessage(Config.MESSAGE_PREFIX + " §7The team §b" + name + "§7 has been created§8.");
            Team.createTeam(name, null, "§e", player.getUniqueId(), TeamStatus.INVITE, null);
            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.setTeam(name);
            getCreatingTeamPlayers().remove(player.getUniqueId());
        } else if(getChangingNamePlayers().contains(player.getUniqueId())) {

            event.setCancelled(true);
            String name = event.getMessage();
            Pattern pattern = Pattern.compile("^[ A-Za-z]+$");
            Matcher matcher = pattern.matcher(name);
            if(name.equalsIgnoreCase("cancel")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cTeam name change has been cancelled§8.");
                changingNamePlayers.remove(player.getUniqueId());
                return;
            }
            if(!matcher.matches()) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe team name can only contain letters and spaces§8.");
                return;
            }
            if(Team.getTeam(name) != null) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cA team with this name already exists§8.");
                return;
            }
            if(name.length() > 30) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe name cannot be longer than 30 characters§8.");
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
            player.sendMessage(Config.MESSAGE_PREFIX + " §7The team got renamed to §b" + name + "§8.");
            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.getTeam().setName(name);
            getChangingNamePlayers().remove(player.getUniqueId());

        } else if(getChangingDescriptionPlayers().contains(player.getUniqueId())) {

            event.setCancelled(true);
            String description = event.getMessage();
            Pattern pattern = Pattern.compile("^[ A-Za-z]+$");
            Matcher matcher = pattern.matcher(description);
            if(description.equalsIgnoreCase("cancel")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cTeam description change has been cancelled§8.");
                changingDescriptionPlayers.remove(player.getUniqueId());
                return;
            }
            if(!matcher.matches()) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe team name can only contain letters and spaces§8.");
                return;
            }
            if(description.length() > 150) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe description cannot be longer than 150 characters§8.");
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
            player.sendMessage(Config.MESSAGE_PREFIX + " §7The team description got changed§8.");
            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.getTeam().setDescription(description);
            getChangingDescriptionPlayers().remove(player.getUniqueId());

        }

    }

    public static void initialize() {

        // MAIN

        Inventory pageMain = Bukkit.createInventory(null, 9*3, "Team Management");
        for(int i = 0; i < pageMain.getSize(); i++) pageMain.setItem(i, ItemListUI.FILLER);
        pageMain.setItem(10, ItemListUI.CANCEL);
        pageMain.setItem(13, ItemListUI.TEAM_BROWSE);
        pageMain.setItem(16, ItemListUI.TEAM_CREATE);

        // SETTINGS

        Inventory pageSettings = Bukkit.createInventory(null, 9*3, "Team Settings");
        for(int i = 0; i < pageSettings.getSize(); i++) pageSettings.setItem(i, ItemListUI.FILLER);
        pageSettings.setItem(10, ItemListUI.PREVIOUS);
        pageSettings.setItem(16, ItemListUI.TEAM_LEAVE);

        // SETTINGS ADMIN

        Inventory pageAdmin = Bukkit.createInventory(null, 9*5, "Team Settings");
        for(int i = 0; i < pageAdmin.getSize(); i++) pageAdmin.setItem(i, ItemListUI.FILLER);
        pageAdmin.setItem(10, ItemListUI.TEAM_CHANGE_NAME);
        pageAdmin.setItem(13, ItemListUI.TEAM_CHANGE_DESCRIPTION);
        pageAdmin.setItem(16, ItemListUI.TEAM_CHANGE_STATUS);
        pageAdmin.setItem(29, ItemListUI.PREVIOUS);
        pageAdmin.setItem(33, ItemListUI.TEAM_LEAVE);

        // CHANGE STATUS

        Inventory pageStatus = Bukkit.createInventory(null, 9*3, "Change Status");
        for(int i = 0; i < pageStatus.getSize(); i++) pageStatus.setItem(i, ItemListUI.FILLER);
        pageStatus.setItem(10, ItemListUI.PREVIOUS);
        pageStatus.setItem(12, ItemListUI.TEAM_CHANGE_STATUS_CLOSED);
        pageStatus.setItem(14, ItemListUI.TEAM_CHANGE_STATUS_INVITE);
        pageStatus.setItem(16, ItemListUI.TEAM_CHANGE_STATUS_OPEN);

        // LEAVE CONFIRM

        Inventory pageLeaveConfirm = Bukkit.createInventory(null, 9*3, "Leave Team");
        for(int i = 0; i < pageLeaveConfirm.getSize(); i++) pageLeaveConfirm.setItem(i, ItemListUI.FILLER);
        pageLeaveConfirm.setItem(16, ItemListUI.CANCEL);
        pageLeaveConfirm.setItem(10, ItemListUI.TEAM_LEAVE_CONFIRM);

        inventories.put(TeamPage.MAIN.getIndex(), pageMain);
        inventories.put(TeamPage.SETTINGS.getIndex(), pageSettings);
        inventories.put(TeamPage.SETTINGS_ADMIN.getIndex(), pageAdmin);
        inventories.put(TeamPage.SETTINGS_STATUS.getIndex(), pageStatus);
        inventories.put(TeamPage.LEAVE_CONFIRM.getIndex(), pageLeaveConfirm);

        int count = Team.getTeams().size();
        int pages = (int) Math.floor((double) count / 27) + 1;
        ArrayList<Team> teams = new ArrayList<>();
        Team.getTeams().forEach((name, team) -> teams.add(team));

        for(int i = 0; i < pages; i++) {

            int currentPage = i+1;

            Inventory pageBrowse = Bukkit.createInventory(null, 9*5, "Browse Teams: Page " + currentPage);
            for(int j = 0; j < 9; j++) pageBrowse.setItem(j, ItemListUI.FILLER);
            for(int j = 36; j < pageBrowse.getSize(); j++) pageBrowse.setItem(j, ItemListUI.FILLER);
            pageBrowse.setItem(40, ItemListUI.CANCEL);
            if(currentPage != 1) pageBrowse.setItem(36, ItemListUI.PREVIOUS);
            if(currentPage < pages) pageBrowse.setItem(44, ItemListUI.NEXT);

            List<Team> teamsInPage;

            if(teams.size() < (currentPage*27)) {
                teamsInPage = teams.subList((i*27), teams.size());
            } else {
                teamsInPage = teams.subList((i*27), (i*27)+27);
            }

            int j = 0;
            for(Team team : teamsInPage) {

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
                    pageBrowse.setItem(j+9, item);
                    j++;

                }

            }

            teamBrowsePages.put(i, pageBrowse);

        }

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

    public static ArrayList<UUID> getChangingNamePlayers() {
        return changingNamePlayers;
    }

    public static ArrayList<UUID> getChangingDescriptionPlayers() {
        return changingDescriptionPlayers;
    }

    public static HashMap<UUID, String> getTeamJoinRequests() {
        return teamJoinRequests;
    }

}
