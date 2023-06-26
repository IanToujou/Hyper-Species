package net.toujoustudios.hyperspecies.data.ability.passive;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveDemon extends PassiveAbility {

    @Override
    public void execute(Player player) {

        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 20, 0, false, false, true));

        if(player.getWorld().getTime() > 12500 && player.getWorld().getTime() < 23500) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 0, false, false, true));
        } else {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            if(block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 0, false, false, true));
            }
        }

        if(player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 0, false, false, true));
            player.damage(1);
        }

        if(player.getWorld().hasStorm() || player.getWorld().isThundering()) {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            if(block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 0, false, false, true));
                player.damage(1);
            }
        }

    }

}
