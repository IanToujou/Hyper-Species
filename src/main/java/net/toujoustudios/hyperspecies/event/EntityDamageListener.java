package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class EntityDamageListener implements Listener {

    private static final HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player player) {

            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.setRegenerationCoolingDown(true);
            playerManager.setHealthRegeneration(0);

            double damage = event.getFinalDamage();
            double health = playerManager.getHealth();
            double shield = playerManager.getShield();
            double trueDamage = damage;

            if(tasks.containsKey(player.getUniqueId())) {
                tasks.get(player.getUniqueId()).cancel();
                tasks.remove(player.getUniqueId());
            }

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    playerManager.setRegenerationCoolingDown(false);
                    tasks.remove(player.getUniqueId());
                }
            }.runTaskLater(HyperSpecies.getInstance(), 400);
            tasks.put(player.getUniqueId(), task);

            if(playerManager.getShield() > 0) {

                if(damage < shield) {
                    trueDamage = 0;
                    player.sendTitle("", "§e⛨", 5, 5, 5);
                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 100, 1f);
                    playerManager.setShield(shield-damage);
                } else {
                    trueDamage = damage-shield;
                    player.sendTitle("", "§c⛨ Break", 5, 10, 5);
                    player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 100, 1f);
                    playerManager.setShield(0);
                }

            }

            event.setDamage(0);
            playerManager.setHealth(health-trueDamage);

        }

    }

}