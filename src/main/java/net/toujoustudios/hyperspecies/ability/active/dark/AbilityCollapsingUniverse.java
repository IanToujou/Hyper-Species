package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class AbilityCollapsingUniverse extends Ability {

    public AbilityCollapsingUniverse() {

        super(
                "Collapsing Universe",
                List.of("§8Enemies will be sent to a pitch", "§8black sphere where the user has a", "§8buff. The only way to escape is to", "§8deal enough damage to you.", "§8The dimension is active for §d{duration}s§8."),
                Element.DARK,
                AbilityType.UTILITY,
                20,
                600,
                Material.DRAGON_EGG,
                5,
                List.of("Demon", "Wolf"),
                10,
                8
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(20, 30, 40, 50, 60, 70));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        Block block = player.getTargetBlock(null, 30);

        Player target = null;
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 5 * 5;
        for (Player all : players) {
            if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared)
                target = all;
        }
        if (target == null) return false;

        Location playerLocation = player.getLocation();
        Location location = target.getLocation();
        World world = location.getWorld();
        assert world != null;

        Block center = location.getBlock();
        int radius = 18;
        int outerRadius = radius+2;
        HashMap<Block, Material> blockTypes = new HashMap<>();

        for (int x = -outerRadius; x <= outerRadius; x++) {
            for (int y = -outerRadius; y <= outerRadius; y++) {
                for (int z = -outerRadius; z <= outerRadius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if (center.getLocation().distance(b.getLocation()) <= outerRadius) {
                        Block copy = world.getBlockAt(new Location(world, b.getX(), 250+y, b.getZ()));
                        blockTypes.put(copy, copy.getType());
                        copy.setType(Material.BLACK_CONCRETE);
                    }
                }
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if (center.getLocation().distance(b.getLocation()) <= radius) {
                        Block copy = world.getBlockAt(new Location(world, b.getX(), 250+y, b.getZ()));
                        copy.setType(Material.AIR);
                    }
                }
            }
        }

        player.teleport(new Location(world, location.getX() + (double) radius / 4, 250-radius+2, location.getZ()));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * duration, 0, false, false, true));
        target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * duration-radius+2, 0, false, false, true));
        target.teleport(new Location(world, location.getX() - (double) radius / 4, 250, location.getZ()));

        world.playSound(new Location(world, location.getX(), 250, location.getZ()), Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, 5, 0.5f);

        Player finalTarget = target;
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            player.teleport(playerLocation);
            finalTarget.teleport(location);
        }, 20L * duration);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> blockTypes.forEach(Block::setType), 20L * (duration+1));

        return true;
    }

}
