package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.ability.active.dark.AbilityCollapsingUniverse;
import net.toujoustudios.hyperspecies.ability.active.dark.AbilityJetBlackSimulation;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(event.getBlock().getType() == Material.REDSTONE_BLOCK) {
            PlayerManager playerManager = PlayerManager.get(player);
            if(playerManager.getSpecies()==null) return;
            ArrayList<UUID> challengers = AbilityJetBlackSimulation.getChallengers();
            challengers.remove(player.getUniqueId());
        }
        if (AbilityJetBlackSimulation.getChallengers().contains(player.getUniqueId()) || AbilityCollapsingUniverse.getChallengers().containsKey(player.getUniqueId())) {
            if(event.getBlock().getType() == Material.BLACK_CONCRETE) event.setCancelled(true);
        }
    }

}
