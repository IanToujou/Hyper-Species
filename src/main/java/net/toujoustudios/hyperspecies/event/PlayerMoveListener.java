package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if (playerManager.isStunned()) event.setCancelled(true);

    }

}
