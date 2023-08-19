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
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

public class AbilityRevengeOfTheGround extends Ability {

    public AbilityRevengeOfTheGround() {

        super(
                "Revenge Of The Ground",
                List.of("§8Any stone blocks in §d{range}m§8 range will", "§8become hostile to enemies, if they", "§8step on them, dealing " + Element.EARTH.getEmoji() + " {damage}§8."),
                Element.EARTH,
                AbilityType.UTILITY,
                7,
                180,
                Material.RAW_GOLD,
                5,
                List.of("Dwarf", "Wolf"),
                3,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.RANGE, List.of(5, 6, 7, 8, 9, 10));
        fields.put(AbilityField.DAMAGE, List.of(3, 4, 5, 6, 7, 8));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int radius = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Block center = player.getLocation().add(0, -1, 0).getBlock();

        List<Material> materials = List.of(
                Material.STONE,
                Material.COBBLESTONE,
                Material.DEEPSLATE,
                Material.COBBLED_DEEPSLATE,
                Material.BLACKSTONE
        );

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                center.getWorld().spawnParticle(Particle.BLOCK_CRACK, new Location(center.getWorld(), center.getX(), center.getY() + 1, center.getZ()), 300, (radius / 2f) - 1, 0.1, (radius / 2f) - 1, Material.DEEPSLATE.createBlockData());
                center.getWorld().playSound(center.getLocation(), Sound.BLOCK_GRASS_BREAK, SoundCategory.MASTER, 3, 0.5f);

                double radiusSquared = radius * radius;
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        for (int z = -radius; z <= radius; z++) {
                            Block b = center.getRelative(x, y, z);
                            if (center.getLocation().distance(b.getLocation()) <= radius) {
                                if (materials.contains(b.getType())) {
                                    Bukkit.getOnlinePlayers().forEach(all -> {
                                        if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(center.getLocation()) <= radiusSquared) {
                                            Block playerBlock = all.getLocation().getBlock().getRelative(BlockFace.DOWN);
                                            if (materials.contains(playerBlock.getType()) && player != all) {
                                                FallingBlock block = all.getWorld().spawnFallingBlock(all.getLocation(), playerBlock.getType().createBlockData());
                                                block.setDropItem(true);
                                                block.setCancelDrop(true);
                                                block.setInvulnerable(true);
                                                block.setVelocity(new Vector(0, 0.6f, 0));
                                                all.damage(damage, player);
                                            }
                                        }

                                    });
                                }
                            }
                        }
                    }
                }

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 0, 20);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20 * 6);

        return true;

    }

}
