package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

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
                2
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
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if(center.getLocation().distance(b.getLocation()) <= radius) {
                        if(materials.contains(b.getType())) blocksFound++;
                    }
                }
            }
        }

        if(blocksFound > 5) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100, 1.3f);
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 0.5, 0.5, 0.5);
        } else if(blocksFound > 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 2f);
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 0.5, 0.5, 0.5);
        } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 0f);

        return true;

    }

}
