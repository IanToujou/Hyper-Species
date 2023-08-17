package net.toujoustudios.hyperspecies.ability.active.fire;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public class AbilityFlameShot extends Ability {

    public AbilityFlameShot() {

        super(
                "Flame Shot",
                List.of("§8Shoot a simple flaming projectile", "§8that does §c{damage} " + Element.FIRE.getEmoji() + " §8damage and sets", "§8the enemy on fire if it hits."),
                Element.FIRE,
                AbilityType.DAMAGE,
                2,
                5,
                Material.SPECTRAL_ARROW,
                5,
                List.of("Demon", "Human", "Reptile", "Feline", "Dwarf", "Wolf"),
                2,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.DAMAGE, List.of(2, 4, 5, 6, 8, 10));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int xp = playerManager.getAbilityExperience(this);
        int level = playerManager.getLevelFromExperience(xp);

        int damage = getFieldValue(AbilityField.DAMAGE, level);

        Arrow projectile = player.launchProjectile(Arrow.class);
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, SoundCategory.MASTER, 2, 0.8f);
        projectile.setCustomName("Flame Shot of " + player.getName());
        projectile.setCustomNameVisible(false);
        projectile.setDamage(damage);
        projectile.setVisualFire(true);
        projectile.setVelocity(projectile.getVelocity().multiply(1.5));

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
