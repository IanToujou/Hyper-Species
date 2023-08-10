package net.toujoustudios.hyperspecies.ability.active.earth;

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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilitySpikingEarth extends Ability {

    public AbilitySpikingEarth() {

        super(
                "Spiking Earth",
                List.of("§8Creates a field during §d{duration}s§8 where sharp", "§8stones are on the ground. Passing", "§8enemies will receive " + Element.EARTH.getEmoji() + " {damage}§8 if they are in", "§8that field."),
                Element.EARTH,
                AbilityType.DAMAGE,
                8,
                240,
                Material.MELON_SEEDS,
                5,
                List.of("Dwarf"),
                4,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(2, 3, 4, 5, 6, 7));
        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                location.getWorld().spawnParticle(Particle.CRIT, location, 300, 2, 0.1, 2);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 6 * 6;
                for (Player all : players) {
                    if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                        if (all != player) all.damage(damage, player);
                    }
                }
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 10, 10);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20L * duration);

        return true;

    }

}
