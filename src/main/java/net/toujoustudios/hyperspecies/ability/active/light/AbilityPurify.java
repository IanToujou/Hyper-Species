package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AbilityPurify extends Ability {

    public AbilityPurify() {

        super(
                "Purify",
                List.of("§8Clear all negative potion effects."),
                Element.LIGHT,
                AbilityType.BUFF,
                12,
                240,
                Material.FEATHER,
                5,
                List.of("Angel"),
                4,
                4,
                null
        );

    }

    @Override
    public boolean execute(Player player) {

        List<PotionEffectType> negativeTypes = List.of(
                PotionEffectType.BAD_OMEN,
                PotionEffectType.BLINDNESS,
                PotionEffectType.CONFUSION,
                PotionEffectType.DARKNESS,
                PotionEffectType.POISON,
                PotionEffectType.SLOW,
                PotionEffectType.SLOW_DIGGING,
                PotionEffectType.UNLUCK,
                PotionEffectType.WEAKNESS,
                PotionEffectType.WITHER
        );

        player.getActivePotionEffects().forEach(effect -> {
            if (negativeTypes.contains(effect.getType())) player.removePotionEffect(effect.getType());
        });
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 50, 0.1, 0.1, 0.1);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2f);
        return true;
    }

}
