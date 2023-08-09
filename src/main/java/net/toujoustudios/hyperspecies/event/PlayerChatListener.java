package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.chat.ChatChannel;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collection;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (TeamUI.getCreatingTeamPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        ChatChannel channel = playerManager.getChannel();

        String format = channel.getFullName() + " §8| §e{Player} §8> " + channel.getChatColor() + "{Message}";

        format = format.replace("{Player}", player.getDisplayName());
        format = format.replace("&", "§");

        event.setCancelled(true);
        String message = format.replace("{Message}", event.getMessage());

        Logger.log(LogLevel.INFORMATION, "(" + channel.getName() + ") " + player.getName() + " > " + message);

        if(channel == ChatChannel.LOCAL) {
            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = 50 * 50;
            players.forEach(all -> {
                if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                    all.sendMessage(message);
                }
            });
        } else if(channel == ChatChannel.GLOBAL) {
            Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(message));
        } else if(channel == ChatChannel.SUPPORT) {
            player.sendMessage(message);
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.hasPermission("hyperspecies.group.moderator") && all != player) all.sendMessage(message);
            });
        } else if(channel == ChatChannel.TEAM) {
            player.sendMessage(message);
            Bukkit.getOnlinePlayers().forEach(all -> {
                PlayerManager manager = PlayerManager.getPlayer(all);
                if(manager.getTeam() == playerManager.getTeam() && all != player) all.sendMessage(message);
            });
        } else if(channel == ChatChannel.ADMIN) {
            player.sendMessage(message);
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all.hasPermission("hyperspecies.group.admin") && all != player) all.sendMessage(message);
            });
        }

    }

}
