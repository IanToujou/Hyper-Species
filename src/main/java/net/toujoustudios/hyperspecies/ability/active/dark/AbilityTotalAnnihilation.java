package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.species.Species;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityTotalAnnihilation extends Ability {

    public AbilityTotalAnnihilation() {

        super(
                "Total Annihilation",
                List.of("§8Summons multiple meteors that destroy", "§8the environment in a radius of §d{range}m", "§8and deal " + Element.FIRE.getEmoji() + " {damage} §8damage."),
                Element.DARK,
                Element.FIRE,
                AbilityType.TERRAIN,
                12,
                210,
                Material.ENDER_PEARL,
                5,
                List.of("Demon"),
                8,
                5,
                Objects.requireNonNull(Species.getSpecies("Demon")).getSubSpecies("Fallen Angel")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.RANGE, List.of(10, 13, 15, 17, 18, 20));
        fields.put(AbilityField.DAMAGE, List.of(15, 17, 19, 21, 23, 25));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 50);
        if (block.getType() == Material.AIR) return false;

        Location impactLocation = block.getLocation();
        Location spawnLocation = impactLocation.add(0, 150, 0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 5, 1f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0, 2, 0), 100, 0, 3, 0);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {

            List<Fireball> entities = List.of(
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX() - 15, spawnLocation.getY(), spawnLocation.getZ() + 15), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ() + 15), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX() + 15, spawnLocation.getY(), spawnLocation.getZ() + 15), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX() - 15, spawnLocation.getY(), spawnLocation.getZ()), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ()), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX() + 15, spawnLocation.getY(), spawnLocation.getZ()), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX() - 15, spawnLocation.getY(), spawnLocation.getZ() - 15), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ() - 15), EntityType.FIREBALL),
                    (Fireball) player.getWorld().spawnEntity(new Location(spawnLocation.getWorld(), spawnLocation.getX() + 15, spawnLocation.getY(), spawnLocation.getZ() - 15), EntityType.FIREBALL)
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
