package net.toujoustudios.hyperspecies.data.ability.passive;

import org.bukkit.Material;
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
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 0, false, false, true));
        }

        if(player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 10, 0, false, false, true));
        }

    }

}
