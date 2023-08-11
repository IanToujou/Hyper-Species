package net.toujoustudios.hyperspecies.event;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.toujoustudios.hyperspecies.chat.ChatChannel;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Random;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        Player player = event.getPlayer();

        if (TeamUI.getCreatingTeamPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        ChatChannel channel = playerManager.getChannel();

        String format = channel.getFullName() + " §8| §e{Player} §8> " + channel.getChatColor() + "{Message}";

        format = format.replace("{Player}", PlainTextComponentSerializer.plainText().serialize(player.displayName()));
        format = format.replace("&", "§");

        String message = PlainTextComponentSerializer.plainText().serialize(event.message());

        if (playerManager.isKawaii()) {

            message = message.replace("l", "w");
            message = message.replace("r", "w");
            message = message.replace("o", "owo");
            message = message.replace("u", "uwu");

            String[] messageArray = message.split(" ");
            StringBuilder newMessage = new StringBuilder();

            for (int i = 0; i < messageArray.length; i++) {
                int random = new Random().nextInt(30);
                if (random == 14) {
                    messageArray[i] += " §dNyaa~";
                } else if (random == 13) {
                    messageArray[i] += " §d*detonates a nuclear warhead*";
                } else if (random == 12) {
                    messageArray[i] += "...";
                } else if (random == 11) {
                    messageArray[i] += " §dMeow~";
                } else if (random == 10) {
                    messageArray[i] += " §dhehe~";
                } else if (random == 9) {
                    messageArray[i] += " §b*clumsy*";
                } else if (random == 8) {
                    messageArray[i] += " §b*giggles*";
                } else if (random == 7) {
                    messageArray[i] += " §d>///<";
                }
                newMessage.append(messageArray[i]).append("§7 ");
            }

            message = newMessage.toString();

        }

        event.setCancelled(true);
        String fullMessage = format.replace("{Message}", message);

        Logger.log(LogLevel.INFORMATION, fullMessage);

        if (channel == ChatChannel.LOCAL) {
            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = 50 * 50;
            players.forEach(all -> {
                if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                    all.sendMessage(fullMessage);
                }
            });
        } else if (channel == ChatChannel.GLOBAL) {
            Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(fullMessage));
        } else if (channel == ChatChannel.SUPPORT) {
            player.sendMessage(fullMessage);
            Bukkit.getOnlinePlayers().forEach(all -> {
                if (all.hasPermission("hyperspecies.group.moderator") && all != player) all.sendMessage(fullMessage);
            });
        } else if (channel == ChatChannel.TEAM) {
            player.sendMessage(fullMessage);
            Bukkit.getOnlinePlayers().forEach(all -> {
                PlayerManager manager = PlayerManager.getPlayer(all);
                if (manager.getTeam() == playerManager.getTeam() && all != player) all.sendMessage(fullMessage);
            });
        } else if (channel == ChatChannel.ADMIN) {
            player.sendMessage(fullMessage);
            Bukkit.getOnlinePlayers().forEach(all -> {
                if (all.hasPermission("hyperspecies.group.admin") && all != player) all.sendMessage(fullMessage);
            });
        }

    }

}
