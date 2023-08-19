package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class AbilityHailstormBarrage extends Ability {

    public AbilityHailstormBarrage() {

        super(
                "Hailstorm Barrage",
                List.of("§8Summons a barrage of icy hailstones", "§8from the sky, dealing " + Element.WATER.getEmoji() + " {damage}§8 and maybe", "§8freezing enemies on impact for §d{duration}s§8."),
                Element.WATER,
                AbilityType.DAMAGE,
                6,
                210,
                Material.ICE,
                5,
                List.of("Reptile", "Angel", "Human", "Aquatilia"),
                3,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 7, 9, 11, 13, 15));
        fields.put(AbilityField.DURATION, List.of(1, 2, 3, 4, 5, 6));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();
        assert location.getWorld() != null;
        ArrayList<FallingBlock> blocks = new ArrayList<>();

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                int x = new Random().nextInt(11) - 5;
                int z = new Random().nextInt(11) - 5;

                Location blockLocation = new Location(location.getWorld(), location.getX() + x, location.getY() + 8, location.getZ() + z);

                FallingBlock block = location.getWorld().spawnFallingBlock(blockLocation, Material.BLUE_ICE.createBlockData());
                block.setDropItem(true);
                block.setCancelDrop(true);
                block.setInvulnerable(true);
                block.setVelocity(new Vector(0, -2f, 0));

                blocks.add(block);
                block.getWorld().playSound(blockLocation, Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 3, 2f);

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 10, 10);

        BukkitTask task2 = new BukkitRunnable() {

            @Override
            public void run() {
                blocks.forEach(b -> {
                    if (b.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
                        b.remove();
                        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                        players.forEach(all -> {
                            if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(b.getLocation()) <= 1) {
                                all.damage(damage, player);
                                int random = new Random().nextInt(4);
                                if (random == 0) {
                                    PlayerManager manager = PlayerManager.get(all);
                                    manager.stun(20 * duration);
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 3, 0f);
                                } else
                                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 3, 1.3f);
                            }
                        });
                    }
                });

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 1, 1);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            task2.cancel();
        }, 20 * 10);

        return true;

    }

}
