package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class AbilityJetBlackSimulation extends Ability {

    private static final HashMap<UUID, Location> challengers = new HashMap<>();
    private static final HashMap<UUID, BukkitTask> tasks = new HashMap<>();

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
                List.of("Wolf"),
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

        PlayerManager playerManager = PlayerManager.get(player);
        playerManager.setHealth(playerManager.getHealth()/4);

        Location location = target.getLocation();
        World world = location.getWorld();
        assert world != null;
        Block center = location.getBlock();
        int radius = 18;
        int outerRadius = radius+2;
        int redstoneCount = 0;
        HashMap<Block, Material> blockTypes = new HashMap<>();

        boolean oldRule = Boolean.TRUE.equals(location.getWorld().getGameRuleValue(GameRule.DO_MOB_SPAWNING));
        location.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, false);

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
                        else if(b.getType() != Material.GRASS || b.getType() != Material.TALL_GRASS) {
                            int random = new Random().nextInt(3);
                            int redstoneRandom = new Random().nextInt(3000);
                            if(random == 0) copy.setType(Material.BLACK_WOOL);
                            if(random == 1) copy.setType(Material.BLACK_TERRACOTTA);
                            if(random == 2) copy.setType(Material.BLACKSTONE);
                            if(redstoneRandom == 0) {
                                copy.setType(Material.REDSTONE_BLOCK);
                                redstoneCount++;
                            }
                        }
                    }
                }
            }
        }

        target.teleport(new Location(world, location.getX(), 250, location.getZ()));
        target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 120, 0, false, false, true));
        target.sendMessage("§8You have been teleported to a pocket dimension...");

        boolean canFindBlock = redstoneCount > 1;
        if(canFindBlock) {
            target.sendMessage("§8You need to find §c1 of " + redstoneCount + "§8 redstone blocks to escape.");
        } else {
            target.sendMessage("§8You need to wait for §d120s§8 to escape.");
        }

        world.playSound(new Location(world, location.getX(), 250, location.getZ()), Sound.ENTITY_WITHER_DEATH, SoundCategory.MASTER, 5, 0f);
        challengers.put(target.getUniqueId(), location);
        Player finalTarget = target;

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                finalTarget.teleport(location);
                if(challengers.containsKey(finalTarget.getUniqueId())) finalTarget.damage(10000, player);
            }

        }.runTaskLater(HyperSpecies.getInstance(), 20L * 120);

        tasks.put(target.getUniqueId(), task);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            blockTypes.forEach(Block::setType);
            location.getWorld().setGameRule(GameRule.DO_MOB_SPAWNING, oldRule);
        }, 20L * 121);

        return true;
    }

    public static HashMap<UUID, Location> getChallengers() {
        return challengers;
    }

    public static HashMap<UUID, BukkitTask> getTasks() {
        return tasks;
    }

}
