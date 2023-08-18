package net.toujoustudios.hyperspecies.ability.active.fire;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public class AbilityFireball extends Ability {

    public AbilityFireball() {

        super(
                "Fireball",
                List.of("§8Throw a fireball that knocks back", "§8the enemy, dealing §c{damage} " + Element.FIRE.getEmoji() + " §8damage."),
                Element.FIRE,
                AbilityType.DAMAGE,
                5,
                30,
                Material.FIRE_CHARGE,
                5,
                List.of("Demon", "Human", "Reptile", "Feline", "Dwarf", "Wolf"),
                4,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.DAMAGE, List.of(5, 6, 8, 10, 15, 18));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        Fireball projectile = player.launchProjectile(Fireball.class);
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, SoundCategory.MASTER, 2, 0.8f);
        projectile.setCustomName("Fireball of " + player.getName());
        projectile.setCustomNameVisible(false);
        projectile.setYield(0);
        projectile.setVisualFire(true);
        projectile.setVelocity(projectile.getVelocity().multiply(2));

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                projectile.getWorld().spawnParticle(Particle.LAVA, projectile.getLocation(), 2, 0.1, 0.1, 0.1);
            }
        }.runTaskTimer(HyperSpecies.getInstance(), 5, 5);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            projectile.remove();
        }, 100);

        return true;

    }

}
