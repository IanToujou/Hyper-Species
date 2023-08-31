package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.ability.active.dark.AbilityJetBlackSimulation;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.REDSTONE_BLOCK) {
            Player player = event.getPlayer();
            PlayerManager playerManager = PlayerManager.get(player);
            if(playerManager.getSpecies()==null) return;
            HashMap<UUID, Location> challengers = AbilityJetBlackSimulation.getChallengers();
            HashMap<UUID, BukkitTask> tasks = AbilityJetBlackSimulation.getTasks();
            if(challengers.containsKey(player.getUniqueId())) {
                player.teleport(challengers.get(player.getUniqueId()));
                challengers.remove(player.getUniqueId());
                tasks.get(player.getUniqueId()).cancel();
                tasks.remove(player.getUniqueId());
            }
        }
    }

}
