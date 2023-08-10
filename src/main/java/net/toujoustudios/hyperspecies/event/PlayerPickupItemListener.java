package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerAttemptPickupItemEvent event) {
        PlayerManager playerManager = PlayerManager.getPlayer(event.getPlayer());
        if (playerManager.isSelectingAbility()) event.setCancelled(true);
    }

}
