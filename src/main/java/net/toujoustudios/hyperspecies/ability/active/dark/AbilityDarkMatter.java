package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityDarkMatter extends Ability {

    public AbilityDarkMatter() {

        super(
                "Dark Matter",
                List.of("§8Summons a particle that attracts", "§8enemies in a §d{range}m§8 range for §d{duration}s§8."),
                Element.DARK,
                AbilityType.DEBUFF,
                13,
                300,
                Material.ENDER_PEARL,
                5,
                List.of("Demon", "Reptile", "Wolf"),
                6,
                5
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.RANGE, List.of(10, 11, 12, 13, 14, 15));
        fields.put(AbilityField.DURATION, List.of(5, 5, 6, 6, 7, 8));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        int range = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));

        Location location = player.getTargetBlock(null, 50).getLocation();
        World world = location.getWorld();
        assert world != null;


        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                world.spawnParticle(Particle.SMOKE_NORMAL, location.clone().add(0,1,0), 40, 0.1, 0.1, 0.1);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, SoundCategory.MASTER, 5, 0.6f);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                for (Player all : players) {
                    if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= range*range) {
                        Vector vector = location.clone().subtract(all.getLocation()).toVector().normalize();
                        all.setVelocity(vector);
                    }
                }
            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20L * duration);

        return true;

    }

}
