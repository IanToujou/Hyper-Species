package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AbilityTyphoon extends Ability {

    public AbilityTyphoon() {

        super(
                "Typhoon",
                List.of("§8Summons a huge storm that may", "§8spawn massive tidal waves, knocking", "§8enemies away, dealing " + Element.WATER.getEmoji() + " {damage}§8 AoE damage.", "§8The storm goes on for §d{duration}s§8."),
                Element.WATER,
                Element.AIR,
                AbilityType.DAMAGE,
                15,
                600,
                Material.GLOW_INK_SAC,
                5,
                List.of("Aquatilia"),
                8,
                5
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 6, 7, 8, 9, 10));
        fields.put(AbilityField.DURATION, List.of(40, 50, 60, 70, 80, 90));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        player.getWorld().setStorm(true);

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                player.getWorld().setStorm(true);
                int randomX = new Random().nextInt(71) - 35;
                int randomZ = new Random().nextInt(71) - 35;
                int x = (int) (randomX + location.getX());
                int z = (int) (randomZ + location.getZ());

                Location loc = new Location(location.getWorld(), x, location.getWorld().getHighestBlockYAt(x, z), z);
                loc.getWorld().strikeLightningEffect(loc);

                new BukkitRunnable() {
                    double t = Math.PI / 4;

                    public void run() {
                        t = t + 0.1 * Math.PI;
                        for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                            double x = t * cos(theta);
                            double y = 2 * Math.exp(-0.1 * t) * sin(t) + 1.5;
                            double z = t * sin(theta);
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

                        if (t > 10) {
                            this.cancel();
                        }
                    }

                }.runTaskTimer(HyperSpecies.getInstance(), 0, 1);

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 20 * 5, 20);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            player.getWorld().setStorm(false);
        }, duration * 20L);

        return true;

    }

}
