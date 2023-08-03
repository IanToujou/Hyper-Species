package net.toujoustudios.hyperspecies.command;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PassiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.passive")) {
            player.sendMessage(Component.text(Config.MESSAGE_ERROR_PERMISSION));
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Component.text(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage())));
            return false;
        }

        boolean enabled = Config.PASSIVES;
        Config.PASSIVES = !enabled;

        player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§7 Passive abilities are now set to§8: §b" + Config.PASSIVES));

        return false;

    }

    public String getUsage() {
        return "/passive";
    }

}
