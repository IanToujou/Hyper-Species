package net.toujoustudios.hyperspecies.ability.passive;

import net.toujoustudios.hyperspecies.item.MaterialType;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PassiveAquatilia extends PassiveAbility {

    @Override
    public void execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);

        boolean inWater = player.getLocation().getBlock().getType() == Material.WATER || player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.WATER || player.getLocation().add(0, 1, 0).getBlock().getType() == Material.WATER || player.getLocation().add(1, 0, 0).getBlock().getType() == Material.WATER || player.getLocation().add(0, 0, 1).getBlock().getType() == Material.WATER || player.getLocation().subtract(1, 0, 0).getBlock().getType() == Material.WATER || player.getLocation().subtract(0, 0, 1).getBlock().getType() == Material.WATER;

        if (inWater) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 20 * 10, 2, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 10, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20 * 10, 1, false, false, true));
        } else {
            if (!player.getWorld().isThundering() && !player.getWorld().hasStorm()) {
                // TODO: Remove in release
                if (!player.getWorld().getName().contains("farmworld")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 2, false, false, true));
                }
            }
        }

        if (player.getWorld().getTime() < 12500 || player.getWorld().getTime() > 23500) {
            if (player.getWorld().getEnvironment() != World.Environment.THE_END) {
                if (!inWater) {
                    Block block = player.getWorld().getHighestBlockAt(player.getLocation());
                    if (!player.getWorld().getName().contains("farmworld")) {
                        if (block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                            List<Biome> hotBiomes = List.of(Biome.DESERT, Biome.BADLANDS);
                            if (hotBiomes.contains(player.getLocation().getBlock().getBiome())) {
                                player.damage(3);
                            } else if (!player.getWorld().isThundering() && !player.getWorld().hasStorm()) {
                                player.damage(2);
                            }
                        }
                    }
                }
            }
        }

        if (player.getWorld().isUltraWarm()) {
            // TODO: Remove in release
            if (!player.getWorld().getName().contains("farmworld")) {
                player.damage(3);
            }
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));

        if (MaterialType.BLOCKS_CORAL.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            playerManager.setHealthRegeneration(0.4);
        } else {
            if (!playerManager.isRegenerationCoolingDown()) playerManager.setHealthRegeneration(0.4);
        }

    }

}
