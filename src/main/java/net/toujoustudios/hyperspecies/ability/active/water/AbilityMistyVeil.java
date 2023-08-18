package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityMistyVeil extends Ability {

    public AbilityMistyVeil() {

        super(
                "Misty Veil",
                List.of("§8Creates a thick fog that obscures the", "§8vision of enemies for §d{duration}s§8."),
                Element.WATER,
                Element.AIR,
                AbilityType.DEBUFF,
                4,
                120,
                Material.BONE_MEAL,
                5,
                List.of("Reptile", "Aquatilia"),
                3,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 7, 9, 11, 13, 15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                player.getWorld().spawnParticle(Particle.CLOUD, location, 200, 2, 2, 2);
                player.getWorld().spawnParticle(Particle.DRIP_WATER, location, 300, 3, 3, 3);
                player.getWorld().playSound(location, Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 3, 2f);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 5 * 5;
                for (Player all : players) {
                    if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared)
                        all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * duration, 0, false, false, true));
                }
            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20L * duration);

        return true;
    }

}
