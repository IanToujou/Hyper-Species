package net.toujoustudios.hyperspecies.data.ability.passive;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveAquatilia extends PassiveAbility {

    @Override
    public void execute(Player player) {

        if(player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * 10, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10, 2, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 3, false, false, true));
        } else {
            if(!player.getWorld().isThundering() && !player.getWorld().hasStorm()) {
                player.damage(1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 0, false, false, true));
            }
        }

        if(player.getLocation().add(0, 1, 0).getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 10, 0, false, false, true));
        }

        if(player.getWorld().isUltraWarm()) {
            player.damage(3);
        }

    }

}
