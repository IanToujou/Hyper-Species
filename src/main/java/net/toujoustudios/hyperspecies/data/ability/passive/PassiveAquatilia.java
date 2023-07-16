package net.toujoustudios.hyperspecies.data.ability.passive;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PassiveAquatilia extends PassiveAbility {

    @Override
    public void execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        // Buffs underwater, debuffs on land
        if(player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 10, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10, 2, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20 * 10, 3, false, false, true));
        } else {
            if(!player.getWorld().isThundering() && !player.getWorld().hasStorm()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 2, false, false, true));
            }
        }

        // Damage in sunlight and hot biomes
        if(player.getWorld().getTime() < 12500 || player.getWorld().getTime() > 23500) {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            if(block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                List<Biome> hotBiomes = List.of(Biome.DESERT, Biome.BADLANDS);
                if(hotBiomes.contains(player.getLocation().getBlock().getBiome())) {
                    player.damage(2);
                } else player.damage(1);
            }
        }

        if(player.getWorld().isUltraWarm()) {
            player.damage(3);
        }

        // Permanent night vision
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));

        List<Material> coralBlocks = List.of(
                Material.BRAIN_CORAL_BLOCK,
                Material.BUBBLE_CORAL_BLOCK,
                Material.FIRE_CORAL_BLOCK,
                Material.HORN_CORAL_BLOCK,
                Material.TUBE_CORAL_BLOCK
        );

        if(coralBlocks.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            playerManager.setHealthRegeneration(0.4);
        } else {
            if(!playerManager.isRegenerationCoolingDown()) playerManager.setHealthRegeneration(0.4);
        }

    }

}
