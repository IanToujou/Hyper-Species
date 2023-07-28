package net.toujoustudios.hyperspecies.data.ability.active.electro;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class AbilityDash extends Ability {

    public AbilityDash() {

        super(
                "Dash",
                List.of("§8Quickly dash in the direction you", "§8are looking at. You can pass through", "§8walls."),
                Element.ELECTRO,
                AbilityType.UTILITY,
                4,
                120,
                Material.STONE,
                8,
                List.of("Dwarf"),
                4,
                3
        );

    }

    @Override
    public boolean execute(Player player) {
        Location location = player.getLocation();
        Vector direction = location.getDirection();
        direction.normalize();
        direction.multiply(4);
        location.add(direction);
        if (location.getBlock().getType() != Material.AIR) return false;
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, SoundCategory.MASTER, 2, 1.5f);
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 100, 0.5, 0.5, 0.5);
        player.teleport(new Location(location.getWorld(), location.getX(), player.getLocation().getY() + 0.5, location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
        player.setVelocity(player.getLocation().getDirection());
        return true;
    }

}
