package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 50, 0.1, 0.1, 0.1);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2f);
        return true;
    }

}
