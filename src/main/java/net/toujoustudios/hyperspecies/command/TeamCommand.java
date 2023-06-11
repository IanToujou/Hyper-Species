package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.team.Team;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.ui.TeamPage;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if(!player.hasPermission("hyperspecies.command.team")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if(args.length != 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

        if(playerManager.getTeam() == null) {
            TeamUI.openInventory(player);
        } else {

            Team team = playerManager.getTeam();

            Inventory inventory = Bukkit.createInventory(null, 9*6, "Team: " + team.getColor() + team.getName());
            for(int i = 0; i < 9; i++) inventory.setItem(i, ItemList.FILLER);
            for(int i = 45; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);

            inventory.setItem(49, ItemList.CANCEL);
            inventory.setItem(4, ItemList.TEAM_SETTINGS);

            ItemStack owner = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta ownerMeta = (SkullMeta) owner.getItemMeta();
            assert ownerMeta != null;
            ownerMeta.setOwningPlayer(Bukkit.getOfflinePlayer(team.getOwner()));
            ownerMeta.setDisplayName("§b" + Bukkit.getOfflinePlayer(team.getOwner()).getName());
            ownerMeta.setLore(List.of("§7Rank: §dOwner"));
            owner.setItemMeta(ownerMeta);
            inventory.setItem(9, owner);

            for(int i = 0; i < team.getMembers().size(); i++) {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                assert skullMeta != null;
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(team.getMembers().get(i)));
                skullMeta.setDisplayName("§b" + Bukkit.getOfflinePlayer(team.getMembers().get(i)).getName());
                skullMeta.setLore(List.of("§7Rank: §eMember"));
                skull.setItemMeta(skullMeta);
                inventory.setItem(10+i, skull);
            }

            player.openInventory(inventory);

        }

        return false;

    }

    public String getUsage() {
        return "/team";
    }

}
