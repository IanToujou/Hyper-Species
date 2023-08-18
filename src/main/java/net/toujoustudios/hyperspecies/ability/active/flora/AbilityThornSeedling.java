package net.toujoustudios.hyperspecies.ability.active.flora;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityThornSeedling extends Ability {

    public AbilityThornSeedling() {
        super(
                "Thorn Seedling",
                List.of("§8Summon a plant that lasts for §d{duration}s§8,", "§8dealing poison to nearby enemies."),
                Element.FLORA,
                AbilityType.DEBUFF,
                6,
                120,
                Material.FLOWERING_AZALEA,
                8,
                List.of("Elf", "Human", "Feline"),
                3,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10, 11, 12, 14));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        Block block = player.getTargetBlock(null, 30);
        if ((block.getType() == Material.GRASS_BLOCK || block.getType() == Material.MOSS_BLOCK || block.getType() == Material.PODZOL) && block.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR) {

            Location location = block.getLocation().add(0, 1, 0);
            assert location.getWorld() != null;
            location.getBlock().setType(Material.FLOWERING_AZALEA);

            BukkitTask task = new BukkitRunnable() {

                @Override
                public void run() {
                    location.getWorld().spawnParticle(Particle.WARPED_SPORE, location, 500, 2, 0.1, 2);
                    Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                    double radiusSquared = 5 * 5;
                    for (Player all : players) {
                        if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared) {
                            all.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * duration, 1, false, false, true));
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
