package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class AbilityCleanse extends Ability {

    public AbilityCleanse() {

        super(
                "Cleanse",
                List.of("§8Clear all potion effects."),
                Element.LIGHT,
                AbilityType.BUFF,
                6,
                180,
                Material.FEATHER,
                5,
                List.of("Elf", "Angel"),
                3,
                3,
                null
        );

    }

    @Override
    public boolean execute(Player player) {
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 50, 0.1, 0.1, 0.1);
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 2, 2f);
        return true;
    }

}
