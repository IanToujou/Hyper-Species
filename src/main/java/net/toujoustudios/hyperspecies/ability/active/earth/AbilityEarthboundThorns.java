package net.toujoustudios.hyperspecies.ability.active.earth;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityEarthboundThorns extends Ability {

    public AbilityEarthboundThorns() {

        super(
                "Earthbound Thorns",
                List.of("§8Grows sharp stones out of the ground", "§8in front of the user, which deals", Element.EARTH.getEmoji() + " {damage} §8to enemies."),
                Element.EARTH,
                AbilityType.DAMAGE,
                8,
                180,
                Material.GOLD_NUGGET,
                8,
                List.of("Dwarf"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 7, 9, 11, 12, 14));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, SoundCategory.MASTER, 2, 0.5f);

        Location loc1 = player.getLocation().add(player.getLocation().getDirection().setY(0).multiply(3));
        Location loc2 = player.getLocation().add(player.getLocation().getDirection().setY(0).multiply(6));
        Block center1 = loc1.getBlock();
        Block center2 = loc2.getBlock();

        int radius = 2;
        ArrayList<Block> thorns = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b1 = center1.getRelative(x, y, z);
                    if (center1.getLocation().distance(b1.getLocation()) <= radius) {
                        if (b1.getType() == Material.AIR && b1.getRelative(BlockFace.DOWN).getType() != Material.AIR && b1.getRelative(BlockFace.DOWN).getType() != Material.POINTED_DRIPSTONE)
                            thorns.add(b1);
                    }
                    Block b2 = center2.getRelative(x, y, z);
                    if (center2.getLocation().distance(b2.getLocation()) <= radius) {
                        if (b2.getType() == Material.AIR && b1.getRelative(BlockFace.DOWN).getType() != Material.AIR && b1.getRelative(BlockFace.DOWN).getType() != Material.POINTED_DRIPSTONE)
                            thorns.add(b2);
                    }
                }
            }
        }

        thorns.forEach(block -> block.setType(Material.POINTED_DRIPSTONE));

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 3 * 3;
        for (Player all : players) {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(loc1) <= radiusSquared) {
                if (all != player) all.damage(damage, player);
            } else if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(loc2) <= radiusSquared) {
                if (all != player) all.damage(damage, player);
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> thorns.forEach(block -> block.setType(Material.AIR)), 20 * 5);

        return true;

    }

}
