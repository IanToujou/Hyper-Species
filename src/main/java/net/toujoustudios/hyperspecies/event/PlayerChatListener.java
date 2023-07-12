package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Random;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if(TeamUI.getCreatingTeamPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if(playerManager.isKawaii()) {

            String message = event.getMessage();
            message = message.replace("l", "w");
            message = message.replace("r", "w");
            message = message.replace("o", "owo");

            String[] messageArray = message.split(" ");
            StringBuilder newMessage = new StringBuilder();

            for(int i = 0; i < messageArray.length; i++) {
                int random = new Random().nextInt(30);
                if(random == 14) {
                    messageArray[i] += " §dNyaa~";
                } else if(random == 13) {
                    messageArray[i] += " §d*detonates a nuclear warhead*";
                } else if(random == 12) {
                    messageArray[i] += "...";
                } else if(random == 11) {
                    messageArray[i] += " §dMeow~";
                } else if(random == 10) {
                    messageArray[i] += " §dhehe~";
                } else if(random == 9) {
                    messageArray[i] += " §b*clumsy*";
                } else if(random == 8) {
                    messageArray[i] += " §b*giggles*";
                } else if(random == 7) {
                    messageArray[i] += " §d>///<";
                }
                newMessage.append(messageArray[i]).append("§7 ");
            }

            event.setMessage(newMessage.toString());

        }

    }

}
