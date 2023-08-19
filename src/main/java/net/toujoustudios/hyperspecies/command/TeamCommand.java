package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.team.Team;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;
import java.util.List;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.team")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        PlayerManager playerManager = PlayerManager.get(player);

        if (args.length == 2 && (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("deny"))) {

            if (playerManager.getTeam() == null) {
                player.sendMessage(Config.MESSAGE_PREFIX + " §cYou are currently not in a team§8.");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Config.MESSAGE_ERROR_PLAYER_INVALID);
                return false;
            }
            if (!TeamUI.getTeamJoinRequests().containsKey(target.getUniqueId())) {
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe player did not send a join request§8.");
                return false;
            }
            if (!TeamUI.getTeamJoinRequests().get(target.getUniqueId()).equals(playerManager.getTeam().getName())) {
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe player did not send a join request§8.");
                return false;
            }

            if (args[0].equalsIgnoreCase("accept")) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You invited §b" + target.getName() + "§7 to your team§8.");
                target.sendMessage(Config.MESSAGE_PREFIX + " §7Your team join request got accepted§8.");

                PlayerManager targetManager = PlayerManager.get(target);
                targetManager.setTeam(playerManager.getTeam().getName());
                playerManager.getTeam().addMember(target.getUniqueId());

            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cYou denied §b" + target.getName() + "'s§c request§8.");
                target.sendMessage(Config.MESSAGE_PREFIX + " §cYour team join request got denied§8.");
            }

            TeamUI.getTeamJoinRequests().remove(target.getUniqueId());
            return false;

        }

        if (args.length != 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

        if (playerManager.getTeam() == null) {
            TeamUI.openInventory(player);
        } else {

            Team team = playerManager.getTeam();

            Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Team: " + team.getColor() + team.getName());
            for (int i = 0; i < 9; i++) inventory.setItem(i, ItemListUI.FILLER);
            for (int i = 45; i < inventory.getSize(); i++) inventory.setItem(i, ItemListUI.FILLER);

            inventory.setItem(49, ItemListUI.CANCEL);
            inventory.setItem(4, ItemListUI.TEAM_SETTINGS);

            ItemStack owner = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta ownerMeta = (SkullMeta) owner.getItemMeta();
            assert ownerMeta != null;
            ownerMeta.setOwningPlayer(Bukkit.getOfflinePlayer(team.getOwner()));
            ownerMeta.setDisplayName("§b" + Bukkit.getOfflinePlayer(team.getOwner()).getName());
            ownerMeta.setLore(List.of("§7Rank: §dOwner"));
            owner.setItemMeta(ownerMeta);
            inventory.setItem(9, owner);

            for (int i = 0; i < team.getMembers().size(); i++) {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                assert skullMeta != null;
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(team.getMembers().get(i)));
                skullMeta.setDisplayName("§b" + Bukkit.getOfflinePlayer(team.getMembers().get(i)).getName());
                skullMeta.setLore(List.of("§7Rank: §eMember"));
                skull.setItemMeta(skullMeta);
                inventory.setItem(10 + i, skull);
            }

            player.openInventory(inventory);

        }

        return false;

    }

    public String getUsage() {
        return "/team";
    }

}
