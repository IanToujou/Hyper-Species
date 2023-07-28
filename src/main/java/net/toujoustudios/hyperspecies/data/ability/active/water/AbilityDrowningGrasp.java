package net.toujoustudios.hyperspecies.data.ability.active.water;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityDrowningGrasp extends Ability {

    public AbilityDrowningGrasp() {

        super(
                "Drowning Grasp",
                List.of("§8Envelops the target in a suffocating", "§8grip, restricting their movement for", "§d{duration}s§8."),
                Element.WATER,
                AbilityType.DEBUFF,
                8,
                300,
                Material.NAUTILUS_SHELL,
                5,
                List.of("Aquatilia"),
                4,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(3,4,5,6,7,8));
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
                location.getWorld().spawnParticle(Particle.DRIP_WATER, location, 300, 3, 3, 3);
                location.getWorld().spawnParticle(Particle.DRIPPING_HONEY, location, 50, 3, 3, 3);
                location.getWorld().playSound(location, Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 3, 1f);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 5 * 5;
                for(Player all : players) {
                    if(all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                        PlayerManager manager = PlayerManager.getPlayer(all);
                        manager.stun(duration * 20);
                    }
                }
            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 2);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20L * duration);
        return true;
    }

}
