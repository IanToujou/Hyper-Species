package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {



    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if(TeamUI.getCreatingTeamPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }

    }

}
