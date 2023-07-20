package net.toujoustudios.hyperspecies.data.ability.active.flora;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class AbilityFertilizingRain extends Ability {

    public AbilityFertilizingRain() {
        super(
                "Fertilizing Rain",
                List.of("§8Apply the bone meal effect to a plant", "§8that the user is looking at§8."),
                Element.FLORA,
                Element.WATER,
                AbilityType.UTILITY,
                10,
                90,
                Material.WHEAT_SEEDS,
                5,
                List.of("Elf", "Feline", "Wolf"),
                4,
                3
        );

    }

    @Override
    public boolean execute(Player player) {

        Block center = player.getTargetBlock(null, 15);
        Location location = center.getLocation();

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                int radius = 4;
                for(int x = -radius; x <= radius; x++) {
                    for(int y = -radius; y <= radius; y++) {
                        for(int z = -radius; z <= radius; z++) {
                            Block block = center.getRelative(x, y, z);
                            if(center.getLocation().distance(block.getLocation()) <= radius) {
                                if(block.getType() == Material.WHEAT || block.getType() == Material.CARROTS || block.getType() == Material.BEETROOTS || block.getType() == Material.MELON_STEM || block.getType() == Material.PUMPKIN_STEM || block.getType() == Material.TORCHFLOWER_CROP || block.getType() == Material.PITCHER_CROP) {
                                    block.applyBoneMeal(BlockFace.UP);
                                    player.playSound(player.getLocation(), Sound.BLOCK_COMPOSTER_FILL_SUCCESS, SoundCategory.MASTER, 100, 1f);
                                }
                            }
                        }
                    }
                }

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 0, 20 * 3);

        BukkitTask task2 = new BukkitRunnable() {
            @Override
            public void run() {
                location.getWorld().spawnParticle(Particle.FALLING_WATER, new Location(location.getWorld(), location.getX(), location.getY()+4, location.getZ()), 50, 2, 0.1, 2);
            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            task2.cancel();
        }, 20 * 10);

        return true;

    }

}
