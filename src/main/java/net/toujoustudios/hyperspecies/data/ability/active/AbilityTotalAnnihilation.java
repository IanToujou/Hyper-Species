package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbilityTotalAnnihilation extends Ability {

    public AbilityTotalAnnihilation() {

        super(
                "Total Annihilation",
                List.of("§8Summon multiple meteors that destroy", "§8the environment in a radius of §d{radius}m" + "§8and deal " + Element.FIRE.getEmoji() + " {damage} §8damage."),
                Element.DARK,
                Element.FIRE,
                AbilityType.TERRAIN,
                12,
                180,
                Material.ENDER_PEARL,
                5,
                List.of("Demon"),
                List.of("Kill 3 players with §bRay of Doom§8.")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5,6,7,8,9,10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 50);
        if(block.getType() == Material.AIR) return false;

        Location impactLocation = block.getLocation();
        Location spawnLocation = impactLocation.add(0, 150, 0);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 3, 1f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 5, 1f);

        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0, 2, 0), 100, 0, 3, 0);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {

            List<Fireball> entities = List.of(
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(0, 0, 0), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(10, 0, 0), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(10, 0, 10), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(0, 0, 10), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(-10, 0, 0), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(-10, 0, -10), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(0, 0, -10), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(10, 0, -10), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(spawnLocation.add(-10, 0, 10), EntityType.FIREBALL)
            );

            entities.forEach(entity -> {
                entity.setCustomName("Total Annihilation of " + player.getName());
                entity.setCustomNameVisible(false);
                entity.setDirection(new Vector(0, -3, 0));
                entity.setIsIncendiary(false);
                entity.setYield(0);
            });

        }, 20 * 5);

        return true;

    }

}
