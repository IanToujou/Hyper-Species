package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityStrikeOfCorruption extends Ability {

    public AbilityStrikeOfCorruption() {

        super(
                "Strike Of Corruption",
                List.of("§8Use half of your §c❤ HP§8 to charge a", "§8projectile, with the darkest flames", "§8of hell, unleashing a massive amount", "§8of energy, dealing " + Element.DARK.getEmoji() + "§8 {damage} AoE damage", "§8in a small area."),
                Element.DARK,
                Element.ELECTRO,
                AbilityType.DAMAGE,
                14,
                600,
                Material.NETHER_STAR,
                8,
                List.of("Demon"),
                12,
                6,
                Objects.requireNonNull(Species.getSpecies("Demon")).getSubSpecies("Fallen Angel")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(20, 25, 30, 35, 40, 45, 50, 55, 60));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        player.damage(playerManager.getHealth() / 2);

        player.getWorld().spawnEntity(player.getLocation().add(0, 3, 0), EntityType.LIGHTNING);
        player.getWorld().spawnParticle(Particle.FLASH, player.getLocation(), 5, 0.1, 0.1, 0.1);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 300, 0.1, 0.1, 0.1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, 10, 0.5f);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {

            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, SoundCategory.MASTER, 10, 1.5f);
            Arrow arrow = player.launchProjectile(Arrow.class);
            arrow.setVelocity(arrow.getVelocity().multiply(1.2f));
            arrow.setDamage(0);
            arrow.setCustomName("Strike Of Corruption of " + player.getName());
            arrow.setBounce(false);

            BukkitTask task = new BukkitRunnable() {

                @Override
                public void run() {
                    arrow.getWorld().spawnParticle(Particle.SMOKE_LARGE, arrow.getLocation(), 50, 0.1, 0.1, 0.1);
                    arrow.getWorld().spawnParticle(Particle.FLAME, arrow.getLocation(), 5, 0.1, 0.1, 0.1);
                    arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_WITHER_AMBIENT, SoundCategory.MASTER, 3, 2f);
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
