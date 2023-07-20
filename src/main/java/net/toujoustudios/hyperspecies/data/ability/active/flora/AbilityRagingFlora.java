package net.toujoustudios.hyperspecies.data.ability.active.flora;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
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

public class AbilityRagingFlora extends Ability {

    public AbilityRagingFlora() {
        super(
                "Raging Flora",
                List.of("§8Summons a plant that buffs nearby", "§8allies, giving them strength for §d{duration}s§8."),
                Element.FLORA,
                AbilityType.BUFF,
                8,
                240,
                Material.TORCHFLOWER,
                8,
                List.of("Elf", "Feline"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10,11,12,13,14,15,16,17,18));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        Block block = player.getTargetBlock(null, 30);
        if((block.getType() == Material.GRASS_BLOCK || block.getType() == Material.MOSS_BLOCK || block.getType() == Material.PODZOL) && block.getLocation().add(0,1,0).getBlock().getType() == Material.AIR) {

            Location location = block.getLocation().add(0,1,0);
            location.getBlock().setType(Material.TORCHFLOWER);

            BukkitTask task = new BukkitRunnable() {

                @Override
                public void run() {
                    location.getWorld().spawnParticle(Particle.CRIMSON_SPORE, location, 500, 3, 0.1, 3);
                    Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                    double radiusSquared = 8 * 8;
                    for(Player all : players) {
                        if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared) {
                            all.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * duration, 1, false, false, true));
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
