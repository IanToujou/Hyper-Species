package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        PlayerManager playerManager = PlayerManager.get((Player) event.getEntity());
        if (playerManager.isSelectingAbility()) event.setCancelled(true);
    }

}
