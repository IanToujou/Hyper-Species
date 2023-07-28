package net.toujoustudios.hyperspecies.data.ability.active.air;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class AbilityICBM extends Ability {

    private static final ArrayList<UUID> activePlayers = new ArrayList<>();

    public AbilityICBM() {
        super(
                "ICBM",
                List.of("§8Select a designated target location,", "§8then launch into the air and strike", "§8the target location, dealing " + Element.AIR.getEmoji() + " {damage}§8 AoE", "§8damage."),
                Element.AIR,
                Element.ELECTRO,
                AbilityType.DAMAGE,
                14,
                300,
                Material.SPYGLASS,
                8,
                List.of("Feline"),
                8,
                5
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(20, 22, 24, 26, 28, 30, 32, 34, 36));
        setFields(fields);
    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        if (player.getWorld().getHighestBlockAt(location).getY() > player.getLocation().getY()) return false;

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 3, 0.8f);
        player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 100, 0.1, 0.1, 0.1, 3);
        player.setVelocity(new Vector(0, 1, 0).multiply(3));

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            Vector direction = player.getLocation().getDirection();
            player.setVelocity(direction.setY(0).normalize().multiply(30));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 3, 0.5f);
            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 300, 1, 1, 1);
        }, 25);

        activePlayers.add(player.getUniqueId());

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (player.getLocation().add(0, -1, 0).getBlock().getType() != Material.AIR) {
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 5, 0f);
                    player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 100, 1, 0.1, 1);
                    player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 30, 3, 0.1, 3);

                    Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                    double radiusSquared = 15 * 15;
                    players.forEach(all -> {
                        if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                            if (all != player) all.damage(damage, player);
                        }
                    });

                    activePlayers.remove(player.getUniqueId());
                    cancel();

                } else {
                    player.spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 100, 0.1, 0.1, 0.1);
                }
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 20, 1);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            if (!task.isCancelled()) {
                task.cancel();
                activePlayers.remove(player.getUniqueId());
            }
        }, 20 * 10);

        return true;

    }

    public static ArrayList<UUID> getActivePlayers() {
        return activePlayers;
    }

}
