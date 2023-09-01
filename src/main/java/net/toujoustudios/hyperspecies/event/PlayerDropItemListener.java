package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.ability.active.dark.AbilityJetBlackSimulation;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.get(player);
        if (playerManager.isSelectingAbility()) event.setCancelled(true);
        if (AbilityJetBlackSimulation.getChallengers().contains(player.getUniqueId())) event.setCancelled(true);
    }

}
