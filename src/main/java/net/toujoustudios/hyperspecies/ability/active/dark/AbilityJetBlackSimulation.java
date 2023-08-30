package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AbilityJetBlackSimulation extends Ability {

    public AbilityJetBlackSimulation() {

        super(
                "Jet Black Simulation",
                List.of("§8Use §d75%§8 of your §c❤ HP§8 to summon a", "§8pocket dimension for §d120s§8. The enemy", "§8sent to that dimension needs to find", "§8a redstone block and break it. If", "§8they don't find it in time, they die."),
                Element.DARK,
                AbilityType.UTILITY,
                30,
                1000,
                Material.DRAGON_EGG,
                5,
                List.of("Demon"),
                12,
                10
        );

    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 20);

        Player target = null;
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 5 * 5;
        for (Player all : players) {
            if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared)
                target = all;
        }
        if (target == null) return false;

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
                        if(b.getType() == Material.WATER) copy.setType(Material.BLACK_STAINED_GLASS);
                        else if(b.getType() == Material.AIR) copy.setType(Material.AIR);
                        else if(b.getType() == Material.GRASS || b.getType() == Material.TALL_GRASS) {
                            int random = new Random().nextInt(3);
                            if(random == 0) copy.setType(Material.BLACK_WOOL);
                            if(random == 1) copy.setType(Material.BLACK_TERRACOTTA);
                            if(random == 2) copy.setType(Material.BLACKSTONE);
                        }
                    }
                }
            }
        }

        target.teleport(new Location(world, location.getX(), 250, location.getZ()));
        target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 120, 0, false, false, true));

        world.playSound(new Location(world, location.getX(), 250, location.getZ()), Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, 5, 0f);

        Player finalTarget = target;
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> finalTarget.teleport(location), 20L * 120);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> blockTypes.forEach(Block::setType), 20L * 121);

        return true;
    }

}
