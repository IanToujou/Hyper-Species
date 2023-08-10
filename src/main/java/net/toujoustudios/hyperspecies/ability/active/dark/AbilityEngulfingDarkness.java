package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityEngulfingDarkness extends Ability {

    public AbilityEngulfingDarkness() {

        super(
                "Engulfing Darkness",
                List.of("§8Blinds, slows and deafens enemies", "§8in a radius of §d{range}m§8 for a duration", "§8of §d{duration}s."),
                Element.DARK,
                Element.FIRE,
                AbilityType.DEBUFF,
                8,
                90,
                Material.HOPPER,
                8,
                List.of("Demon"),
                8,
                4,
                Objects.requireNonNull(Species.getSpecies("Demon")).getSubSpecies("Hellspawn")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.RANGE, List.of(10, 12, 14, 16, 18, 20, 22, 24, 25));
        fields.put(AbilityField.DURATION, List.of(20, 22, 24, 26, 28, 30, 32, 34, 36));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        int range = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));

        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 3, 0f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 1000, 3, 0, 3);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = range * range;
        players.forEach(all -> {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                if (all != player)
                    all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * duration, 0, false, false, true));
                if (all != player)
                    all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * duration, 5, false, false, true));
            }
        });

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                players.forEach(all -> {
                    if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                        if (all != player) all.stopAllSounds();
                    }
                });
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 2, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20L * duration);

        return true;

    }

}
