package net.toujoustudios.hyperspecies.data.ability.passive;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveDwarf extends PassiveAbility {

    @Override
    public void execute(Player player) {

        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10, 1, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 0, false, false, true));

        // TODO: Add fire resistance and strength when drunk

        // TODO: Weakness if sober

        if(player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 5, 2, false, false, true));
        }

    }

}
