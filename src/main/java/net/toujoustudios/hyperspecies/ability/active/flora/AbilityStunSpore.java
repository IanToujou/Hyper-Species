package net.toujoustudios.hyperspecies.ability.active.flora;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityStunSpore extends Ability {

    public AbilityStunSpore() {
        super(
                "Stun Spore",
                List.of("§8Create a dangerous plant that stuns", "§8enemies if they are in range during§8.", "§d{duration}s§8."),
                Element.FLORA,
                AbilityType.DAMAGE,
                8,
                360,
                Material.PITCHER_PLANT,
                8,
                List.of("Elf", "Feline"),
                6,
                5
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 7, 9, 11, 13, 15, 17, 19, 20));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        Block block = player.getTargetBlock(null, 30);
        if ((block.getType() == Material.GRASS_BLOCK || block.getType() == Material.MOSS_BLOCK || block.getType() == Material.PODZOL) && block.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR) {

            Location location = block.getLocation().add(0, 1, 0);
            location.getBlock().setType(Material.PITCHER_PLANT);

            BukkitTask task = new BukkitRunnable() {

                @Override
                public void run() {
                    location.getWorld().spawnParticle(Particle.FALLING_OBSIDIAN_TEAR, new Location(location.getWorld(), location.getX(), location.getY() + 2, location.getZ()), 100, 2, 0.1, 2);
                    Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                    double radiusSquared = 7 * 7;
                    for (Player all : players) {
                        if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared) {
                            if (all != player) {
                                PlayerManager allManager = PlayerManager.getPlayer(all);
                                if (!allManager.isStunned()) {
                                    allManager.stun(duration);
                                }
                            }
                        }
                    }
                }

            }.runTaskTimer(HyperSpecies.getInstance(), 10, 10);

            Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
                task.cancel();
                location.getBlock().setType(Material.AIR);
            }, 20L * duration);
            return true;

        }

        return false;

    }

}
