package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {

        if (event.getEntity() instanceof Player player) {

            if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC_REGEN) {
                PlayerManager playerManager = PlayerManager.getPlayer(player);
                playerManager.setHealth(playerManager.getHealth() + event.getAmount());
            }

        }

    }

}
