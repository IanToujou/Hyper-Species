package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AbilityTidalWave extends Ability {

    public AbilityTidalWave() {

        super(
                "Tidal Wave",
                List.of("§8Summons a tidal wave that knocks", "§8enemies away, dealing " + Element.WATER.getEmoji() + " {damage}§8 AoE damage."),
                Element.WATER,
                AbilityType.DAMAGE,
                5,
                90,
                Material.PRISMARINE_SHARD,
                5,
                List.of("Reptile", "Aquatilia"),
                3,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();

        new BukkitRunnable() {
            double t = Math.PI / 4;
            final Location loc = player.getLocation();

            public void run() {
                t = t + 0.1 * Math.PI;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * cos(theta);
                    double y = 2 * Math.exp(-0.1 * t) * sin(t) + 1.5;
                    double z = t * sin(theta);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(Particle.FALLING_WATER, loc, 5, 0.1, 0.1, 0.1);
                    for (Player all : players) {
                        if (all.getWorld() == player.getWorld()) {
                            if(all.getLocation().distanceSquared(loc) <= 1) {
                                if (all != player) {
                                    all.damage(damage, player);
                                    all.setVelocity(new Vector(0, 0.5, 0));
                                }
                            }
                        }
                    }
                    loc.subtract(x, y, z);

                    theta = theta + Math.PI / 64;

                    x = t * cos(theta);
                    y = 2 * Math.exp(-0.1 * t) * sin(t) + 1.5;
                    z = t * sin(theta);
                    loc.add(x, y, z);
                    player.getWorld().spawnParticle(Particle.FALLING_WATER, loc, 5, 0.1, 0.1, 0.1);
                    for (Player all : players) {
                        if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(loc) <= 1) {
                            if (all != player) {
                                all.damage(damage, player);
                                all.setVelocity(new Vector(0, 0.5, 0));
                            }
                        }
                    }
                    loc.subtract(x, y, z);
                }

                player.getWorld().playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 3, 1.5f);

                if (t > 10) {
                    this.cancel();
                }
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 0, 1);

        return true;

    }

}
