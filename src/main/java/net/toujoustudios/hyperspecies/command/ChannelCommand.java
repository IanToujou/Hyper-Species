package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.chat.ChatChannel;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class ChannelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.channel")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        String channel = args[0].toUpperCase();

        if (!channel.equalsIgnoreCase("LOCAL") && !channel.equalsIgnoreCase("GLOBAL") && !channel.equalsIgnoreCase("SUPPORT") && !channel.equalsIgnoreCase("ADMIN") && !channel.equalsIgnoreCase("TEAM")) {
            player.sendMessage(Config.MESSAGE_PREFIX + "§c The chat channel does not exist§8.");
            player.sendMessage(Config.MESSAGE_PREFIX + "§c Valid channels§8: §bLocal, Global, Support, Admin, Team");
            return false;
        }

        ChatChannel chatChannel = ChatChannel.valueOf(channel);
        PlayerManager playerManager = PlayerManager.get(player);

        playerManager.setChannel(chatChannel);

        player.sendMessage(Config.MESSAGE_PREFIX + "§7 Your channel has been set to§8: " + chatChannel.getFullName());
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 1f);

        return false;

    }

    public String getUsage() {
        return "/channel <channel>";
    }

}
