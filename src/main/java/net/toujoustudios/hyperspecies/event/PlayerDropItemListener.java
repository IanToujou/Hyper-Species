package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        if (playerManager.isSelectingAbility()) event.setCancelled(true);
    }

}
