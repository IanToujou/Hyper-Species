package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class AbilityMineSight extends Ability {

    public AbilityMineSight() {

        super(
                "Mine Sight",
                List.of("§8Scan the surrounding area for any", "§8coal, iron or gold ore."),
                Element.EARTH,
                AbilityType.UTILITY,
                3,
                60,
                Material.IRON_INGOT,
                5,
                List.of("Dwarf", "Wolf"),
                2,
                1
        );

    }

    @Override
    public boolean execute(Player player) {

        Location location = player.getLocation();
        Block center = location.add(0, -1, 0).getBlock();
        List<Material> materials = List.of(
                Material.COAL_ORE,
                Material.DEEPSLATE_COAL_ORE,
                Material.IRON_ORE,
                Material.DEEPSLATE_IRON_ORE,
                Material.GOLD_ORE,
                Material.DEEPSLATE_GOLD_ORE
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

        if(blocksFound > 10) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100, 1.3f);
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 0.5, 0.5, 0.5);
        } else if(blocksFound > 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 2f);
            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 0.5, 0.5, 0.5);
        } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 0f);

        return true;

    }

}
