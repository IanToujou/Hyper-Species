package net.toujoustudios.hyperspecies.ability.active.psychic;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
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
import java.util.Objects;

public class AbilityPsychicWard extends Ability {

    public AbilityPsychicWard() {
        super(
                "Psychic Ward",
                List.of("§8Summons a plant that gives nearby", "§8enemies nausea for §d{duration}s§8."),
                Element.PSYCHIC,
                AbilityType.DEBUFF,
                5,
                180,
                Material.CHORUS_FLOWER,
                8,
                List.of("Elf"),
                4,
                2,
                Objects.requireNonNull(Species.getSpecies("Elf")).getSubSpecies("Dryad")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10, 11, 12, 13, 14, 15, 16, 17, 18));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 30);

        if (block.getType() != Material.AIR && block.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR) {

            Location location = block.getLocation().add(0, 1, 0);
            assert location.getWorld() != null;
            location.getBlock().setType(Material.TORCHFLOWER);

            BukkitTask task = new BukkitRunnable() {

                @Override
                public void run() {
                    location.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 500, 3, 0.1, 3);
                    Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                    double radiusSquared = 8 * 8;
                    for (Player all : players) {
                        if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared) {
                            if (all != player)
                                all.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * duration, 0, false, false, true));
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
