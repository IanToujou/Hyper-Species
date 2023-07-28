package net.toujoustudios.hyperspecies.data.ability.active.fairy;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public class AbilityCelestialShot extends Ability {

    public AbilityCelestialShot() {

        super(
                "Celestial Shot",
                List.of("§8Shoot an arrow with celestial energy", "§8dealing " + Element.FAIRY.getEmoji() + " {damage}§8 AoE damage and stunning", "§8the enemy during §d{duration}s§8."),
                Element.FAIRY,
                Element.ELECTRO,
                AbilityType.DAMAGE,
                16,
                540,
                Material.NETHER_STAR,
                8,
                List.of("Elf", "Angel"),
                10,
                6
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(10, 15, 20, 25, 30, 35, 40, 45, 50));
        fields.put(AbilityField.DURATION, List.of(5, 5, 6, 6, 7, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        player.getWorld().spawnParticle(Particle.FLASH, player.getLocation(), 5, 0.1, 0.1, 0.1);
        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 200, 0.1, 0.1, 0.1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.MASTER, 10, 0.5f);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {

            player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, SoundCategory.MASTER, 10, 2f);
            Arrow arrow = player.launchProjectile(Arrow.class);
            arrow.setVelocity(arrow.getVelocity().multiply(1.3f));
            arrow.setDamage(0);
            arrow.customName(Component.text("Celestial Shot of " + player.getName()));
            arrow.setVisualFire(false);

            BukkitTask task = new BukkitRunnable() {

                @Override
                public void run() {
                    arrow.getWorld().spawnParticle(Particle.CHERRY_LEAVES, arrow.getLocation(), 100, 1, 1, 1);
                    arrow.getWorld().spawnParticle(Particle.FLASH, arrow.getLocation(), 1, 0.1, 0.1, 0.1);
                    arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.MASTER, 3, 1f);
                }

            }.runTaskTimer(HyperSpecies.getInstance(), 1, 1);

            Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
                arrow.remove();
                task.cancel();
            }, 20 * 10);

        }, 20 * 5);

        return true;

    }

}
