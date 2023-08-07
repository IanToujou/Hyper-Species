package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.chat.ChatChannel;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collection;
import java.util.Random;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (TeamUI.getCreatingTeamPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        ChatChannel channel = playerManager.getChannel();

        String format = "§2\uD83C\uDFF4 §8| §e{Player} §8> §7{Message}";

        if(channel == ChatChannel.GLOBAL) {
            format = "§9\uD83C\uDF0D §8| §e{Player} §8> §b{Message}";
        } else if(channel == ChatChannel.SUPPORT) {
            format = "§5\uD83C\uDF10 §8| §e{Player} §8> §d{Message}";
        }

        format = format.replace("{Player}", player.getDisplayName());
        format = format.replace("&", "§");

        if (playerManager.isKawaii()) {

            String message = event.getMessage();
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

            event.setMessage(newMessage.toString());

        }

        event.setCancelled(true);
        String message = format.replace("{Message}", event.getMessage());

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
            player.sendMessage(event.getMessage());
            Bukkit.getOnlinePlayers().forEach(all -> {
                player.sendMessage(message);
                if(all.hasPermission("hyperspecies.group.moderator") && all != player) {
                    all.sendMessage(message);
                    all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 2f);
                }
            });
        }

    }

}
