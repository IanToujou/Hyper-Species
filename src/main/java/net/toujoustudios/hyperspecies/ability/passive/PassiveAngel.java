package net.toujoustudios.hyperspecies.ability.passive;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveAngel extends PassiveAbility {

    @Override
    public void execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        // Slow fall on shift
        if (player.isSneaking()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0, false, false, true));
        }

        // Regeneration in sunlight and weakness at night
        if (player.getWorld().getTime() < 12500 || player.getWorld().getTime() > 23500 && !player.getWorld().isThundering() && !player.getWorld().hasStorm()) {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            if (block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 0, false, false, true));
                playerManager.setHealthRegeneration(0.2);
            }
        } else {
            // TODO: Remove in release
            if (!player.getWorld().getName().contains("farmworld")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 0, false, false, true));
            }
        }

        // Slowness in water
        if (player.getLocation().getBlock().getType() == Material.WATER) {
            // TODO: Remove in release
            if (!player.getWorld().getName().contains("farmworld")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 3, 0, false, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 1, false, false, true));
            }
        }

        // Mana regeneration on gold
        if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.GOLD_BLOCK) {
            playerManager.setManaRegeneration(0.3);
        } else {
            playerManager.setManaRegeneration(0.1);
        }

        if (player.getWorld().isUltraWarm()) {
            // TODO: Remove in release
            if (!player.getWorld().getName().contains("farmworld")) {
                player.damage(3);
            }
        }

    }

}
