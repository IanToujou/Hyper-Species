package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class AbilityBurningRain extends Ability {

    public AbilityBurningRain() {

        super(
                "Burning Rain",
                List.of("§8Shoot a projectile that summons a", "§8cloud on hit, unleashing a burning", "§8rain that does " + Element.FIRE.getEmoji() + " §c{damage}§8 damage."),
                Element.FIRE,
                AbilityType.DAMAGE,
                6,
                60,
                Material.BLAZE_POWDER,
                5,
                List.of("Demon", "Reptile", "Human", "Feline", "Dwarf", "Wolf"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5,7,9,11,13,15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        Arrow arrow = player.launchProjectile(Arrow.class);
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            Location location = arrow.getLocation();
            arrow.remove();
            location.getWorld().spawnParticle(Particle.LAVA, location, 50, 0.1, 0.1, 0.1);
            location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.MASTER, 5, 0.5f);
            location.getWorld().spawnEntity(location, EntityType.LIGHTNING);
        }, 10);

        ArrayList<Arrow> arrowList = new ArrayList<>();

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                Location location = arrow.getLocation();
                Arrow flameArrow = location.getWorld().spawn(location, Arrow.class);
                int randomX = new Random().nextInt(100)-50;
                int randomZ = new Random().nextInt(100)-50;
                flameArrow.setVelocity(new Vector((float)randomX/100, -1, (float)randomZ/100));
                flameArrow.setVisualFire(true);
                flameArrow.setDamage(damage);
                arrowList.add(flameArrow);

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 10, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            arrowList.forEach(Entity::remove);
        }, 20 * 10);

        return true;

    }

}
