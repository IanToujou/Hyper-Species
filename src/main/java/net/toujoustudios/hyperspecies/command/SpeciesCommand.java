package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.ui.SpeciesUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class SpeciesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.species")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        PlayerManager playerManager = PlayerManager.get(player);

        if (playerManager.getSpecies() == null) {
            SpeciesUI.openInventory(player);
        } else {
            player.sendMessage(Config.MESSAGE_PREFIX + "§c You already selected a species§8.");
        }

        return false;

    }

    public String getUsage() {
        return "/setspecies <species> [<player>]";
    }

}
