package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class AbilityBite extends Ability {

    public AbilityBite() {

        super(
                "Bite",
                List.of("§8Bite your enemy, dealing " + Element.DARK.getEmoji() + " {damage} damage."),
                Element.DARK,
                AbilityType.DAMAGE,
                5,
                60,
                Material.RABBIT_FOOT,
                8,
                List.of("Feline", "Wolf"),
                2,
                2
        );

        addField(AbilityField.DAMAGE, List.of(5, 6, 7, 8, 9, 10, 12, 14, 15));

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();
        Vector direction = location.getDirection();
        Location impactLocation = location.clone().add(direction.multiply(2));

        int radiusSquared = 3 * 3;
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(impactLocation) <= radiusSquared)
                all.damage(damage, player);
        }

        player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, impactLocation, 5, 0.1, 0.3, 0.1);
        player.getWorld().playSound(impactLocation, Sound.ENTITY_WOLF_GROWL, SoundCategory.MASTER, 2, 1);

        return true;

    }

}
