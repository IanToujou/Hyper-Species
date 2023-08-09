package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.species.Species;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;

public class AbilityWayOfTheDwarf extends Ability {

    public AbilityWayOfTheDwarf() {

        super(
                "Way Of The Dwarf",
                List.of("§8Scan the surrounding area for any", "§8diamond, emerald or lapis ore."),
                Element.EARTH,
                AbilityType.UTILITY,
                5,
                120,
                Material.GOLD_INGOT,
                5,
                List.of("Dwarf"),
                5,
                2,
                Objects.requireNonNull(Species.getSpecies("Dwarf")).getSubSpecies("Blacksmith")
        );

    }

    @Override
    public boolean execute(Player player) {

        Location location = player.getLocation();
        Block center = location.add(0, -1, 0).getBlock();
        List<Material> materials = List.of(
                Material.LAPIS_ORE,
                Material.DEEPSLATE_LAPIS_ORE,
                Material.DIAMOND_ORE,
                Material.DEEPSLATE_DIAMOND_ORE,
                Material.EMERALD_ORE,
                Material.DEEPSLATE_EMERALD_ORE
        );

        int radius = 8;
        int blocksFound = 0;
        double space = 0.1;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if (center.getLocation().distance(b.getLocation()) <= radius) {
                        if (materials.contains(b.getType())) {
                            Location point1 = player.getLocation().add(0, 1, 0);
                            Location point2 = b.getLocation();
                            World world = point1.getWorld();
                            if (point1.getWorld() != point2.getWorld()) return false;
                            double distance = point1.distance(point2);
                            Vector p1 = point1.toVector();
                            Vector p2 = point2.toVector();
                            Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
                            double length = 0;
                            for (; length < distance; p1.add(vector)) {
                                world.spawnParticle(Particle.VILLAGER_HAPPY, p1.getX(), p1.getY(), p1.getZ(), 5, 0.1, 0.1, 0.1);
                                length += space;
                            }
                            blocksFound++;
                        }
                    }
                }
            }
        }

        if (blocksFound > 5) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100, 1.3f);
        } else if (blocksFound > 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 2f);
        } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 0f);

        return true;

    }

}
