package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.ability.active.AbilityEnhancingFlame;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if(event.getEntity() instanceof Player player) {

            if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                return;
            }

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            if(playerManager.getSpecies() == null) return;

            playerManager.setRegenerationCoolingDown(true);
            playerManager.setHealthRegeneration(0);

            double damage = event.getFinalDamage();
            double health = playerManager.getHealth();
            double shield = playerManager.getShield();
            double trueDamage = damage;

            if(event.getDamager() instanceof Player dealer) {

                PlayerManager dealerManager = PlayerManager.getPlayer(dealer);
                if(dealerManager.getSpecies() != null && dealerManager.getSpecies().getName().equals("Wolf")) {
                    if(playerManager.getHealth() < playerManager.getMaxHealth() / 3) {
                        trueDamage *= 2;
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getLocation(), 100, Material.REDSTONE_BLOCK.createBlockData());
                        dealer.getWorld().playSound(dealer.getLocation(), Sound.ENTITY_SLIME_DEATH, SoundCategory.MASTER, 1, 0.5f);
                    }
                }

                if(AbilityEnhancingFlame.getPlayers().contains(dealer.getUniqueId())) {
                    player.setFireTicks(20*5);
                }

            }

            if(playerManager.getSpecies().getName().equals("Wolf")) {
                AtomicBoolean isWolfNearby = new AtomicBoolean(false);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 50 * 50;
                players.forEach(all -> {
                    if(all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                        if(all != player) {
                            PlayerManager allManager = PlayerManager.getPlayer(all);
                            if(allManager.getSpecies().getName().equals("Wolf")) {
                                isWolfNearby.set(true);
                            }
                        }
                    }
                });
                if(!isWolfNearby.get()) {
                    trueDamage *= 1.3f;
                }
            }

            if(EntityDamageListener.getTasks().containsKey(player.getUniqueId())) {
                EntityDamageListener.getTasks().get(player.getUniqueId()).cancel();
                EntityDamageListener.getTasks().remove(player.getUniqueId());
            }

            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    playerManager.setRegenerationCoolingDown(false);
                    EntityDamageListener.getTasks().remove(player.getUniqueId());
                }
            }.runTaskLater(HyperSpecies.getInstance(), 400);
            EntityDamageListener.getTasks().put(player.getUniqueId(), task);

            if(playerManager.getShield() > 0) {

                if(damage < shield) {
                    trueDamage = 0;
                    player.sendTitle("", "§e⛨", 5, 10, 5);
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

            player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getLocation().add(0, 0.5, 0), 50, Material.REDSTONE_BLOCK.createBlockData());

        }

    }

}
