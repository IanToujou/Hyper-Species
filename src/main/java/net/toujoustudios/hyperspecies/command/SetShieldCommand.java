package net.toujoustudios.hyperspecies.command;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetShieldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.setshield")) {
            player.sendMessage(Component.text(Config.MESSAGE_ERROR_PERMISSION));
            return false;
        }

        if (args.length == 1) {

            try {

                int shield = Integer.parseInt(args[0]);
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You set your shield to §e⛨ " + shield + "§8.");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);

                PlayerManager playerManager = PlayerManager.getPlayer(player);
                playerManager.setShield(shield);

            } catch (Exception exception) {
                player.sendMessage(Config.MESSAGE_ERROR_INTEGER_INVALID);
            }

        } else if (args.length == 2) {

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                player.sendMessage(Config.MESSAGE_ERROR_PLAYER_INVALID);
                return false;
            }

            try {

                int shield = Integer.parseInt(args[0]);
                PlayerManager playerManager = PlayerManager.getPlayer(target);
                playerManager.setShield(shield);

                player.sendMessage(Config.MESSAGE_PREFIX + " §7You set the shield of §e" + target.getName() + " §7to §e⛨ " + shield + "§8.");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);

            } catch (Exception exception) {
                player.sendMessage(Config.MESSAGE_ERROR_INTEGER_INVALID);
            }

        } else {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        return false;

    }

    public String getUsage() {
        return "/setshield <amount> [<player>]";
    }

}
