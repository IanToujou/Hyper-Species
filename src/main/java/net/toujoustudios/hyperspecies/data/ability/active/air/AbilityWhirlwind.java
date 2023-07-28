package net.toujoustudios.hyperspecies.data.ability.active.air;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class AbilityWhirlwind extends Ability {

    public AbilityWhirlwind() {

        super(
                "Whirlwind",
                List.of("§8Creates a whirlwind that knocks back", "§8enemies§8."),
                Element.AIR,
                AbilityType.UTILITY,
                4,
                80,
                Material.COBWEB,
                5,
                List.of("Angel", "Feline", "Wolf"),
                3,
                2
        );

    }

    @Override
    public boolean execute(Player player) {

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        Location location = player.getLocation();

        new BukkitRunnable() {
            double y = 0;

            @Override
            public void run() {

                if (y >= 4) this.cancel();

                int radius = 3;
                double x = radius * Math.cos(y * 6);
                double z = radius * Math.sin(y * 6);

                for (int i = 0; i < 20; i++) {
                    player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z), 4, 0.1, 0.1, 0.1);
                    y += 0.01;
                    x = radius * Math.cos(y * 6);
                    z = radius * Math.sin(y * 6);
                }

                player.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.MASTER, 2, 2f);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 3 * 3;
                for (Player all : players) {
                    if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                        all.setVelocity(new Vector(0, 0.8, 0));
                    }
                }

            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 1);

        return true;

    }

}
