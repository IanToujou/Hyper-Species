package net.toujoustudios.hyperspecies.data.ability.passive;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveFeline extends PassiveAbility {

    @Override
    public void execute(Player player) {

        // Permanent night vision
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));

        if(player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 5, 99, false, false, true));
        }

        // Speed except for tight places
        if(player.getLocation().add(0, 2, 0).getBlock().getType() != Material.AIR) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 2, 2, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 2, 1, false, false, true));
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1, false, false, true));
        }

    }

}
