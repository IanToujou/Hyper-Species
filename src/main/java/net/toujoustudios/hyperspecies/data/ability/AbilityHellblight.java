package net.toujoustudios.hyperspecies.data.ability;

import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AbilityHellblight extends Ability {

    public AbilityHellblight() {
        super("Hellblight", ItemList.DEMON_ABILITY_HELLBLIGHT, 1, 1);
    }

    @Override
    public void execute(Player player) {

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 3, 1.5f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 300, 5, 0, 5);

    }

}
