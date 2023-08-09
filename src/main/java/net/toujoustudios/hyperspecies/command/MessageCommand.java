package net.toujoustudios.hyperspecies.command;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (args.length < 2) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(Component.text(Config.MESSAGE_ERROR_PLAYER_INVALID));
            return false;
        }

        if (target == player) {
            player.sendMessage(Config.MESSAGE_ERROR_PLAYER_SELF);
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]);
            if (i != (args.length - 1)) messageBuilder.append(" ");
        }

        String message = messageBuilder.toString();

        player.sendMessage(Component.text("§6Private §8| §7To §e" + target.getName() + " §8> §a" + message));
        target.sendMessage(Component.text("§6Private §8| §7From §e" + player.getName() + " §8> §a" + message));

        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 2f);

        return false;

    }

    public String getUsage() {
        return "/message <player> <message>";
    }

}