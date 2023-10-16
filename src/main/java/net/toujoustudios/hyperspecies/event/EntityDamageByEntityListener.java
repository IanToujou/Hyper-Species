package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.ability.active.dark.AbilityCollapsingUniverse;
import net.toujoustudios.hyperspecies.ability.active.dark.AbilityTheHunt;
import net.toujoustudios.hyperspecies.ability.active.earth.AbilityBornIntoStone;
import net.toujoustudios.hyperspecies.ability.active.fire.AbilityDemonicRage;
import net.toujoustudios.hyperspecies.ability.active.fire.AbilityEndblaze;
import net.toujoustudios.hyperspecies.ability.active.fire.AbilityEnhancingFlame;
import net.toujoustudios.hyperspecies.ability.active.fire.AbilityHellblaze;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player) {

            if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                return;
            }

            PlayerManager playerManager = PlayerManager.get(player);

            if (playerManager.getSpecies() == null) return;

            if (AbilityBornIntoStone.getPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            playerManager.setRegenerationCoolingDown(true);
            playerManager.setHealthRegeneration(0);

            double damage = event.getFinalDamage();
            double health = playerManager.getHealth();
            double shield = playerManager.getShield();
            double trueDamage = damage;

            if (event.getDamager() instanceof Player dealer) {

                PlayerManager dealerManager = PlayerManager.get(dealer);
                if (dealerManager.getSpecies() != null && dealerManager.getSpecies().name().equals("Wolf")) {
                    if (playerManager.getHealth() < playerManager.getMaxHealth() / 3) {
                        trueDamage *= 2;
                        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getLocation(), 100, Material.REDSTONE_BLOCK.createBlockData());
                        dealer.getWorld().playSound(dealer.getLocation(), Sound.ENTITY_SLIME_DEATH, SoundCategory.MASTER, 1, 0.5f);
                    }
                }

                if (AbilityCollapsingUniverse.getChallengers().containsKey(player.getUniqueId())) {
                    int times = AbilityCollapsingUniverse.getChallengers().get(player.getUniqueId());
                    AbilityCollapsingUniverse.getChallengers().put(player.getUniqueId(), times + 1);
                }

                if (AbilityTheHunt.getPlayers().contains(player.getUniqueId())) {
                    dealer.giveExp(1);
                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, dealer.getLocation(), 10, 0, 0, 0);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 2, 1f);
                }

                if (AbilityEnhancingFlame.getPlayers().contains(dealer.getUniqueId())) {
                    int random = new Random().nextInt(3);
                    if (random == 0) player.setFireTicks(20 * 2);
                }

                if (AbilityHellblaze.getPlayers().contains(dealer.getUniqueId())) {
                    player.setFireTicks(20 * 5);
                    player.getWorld().playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, SoundCategory.MASTER, 1, 2f);
                }

                if (AbilityEndblaze.getPlayers().contains(dealer.getUniqueId())) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5 * 20, 0, false, false, true));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                }

                if (AbilityDemonicRage.getUsingPlayers().contains(dealer.getUniqueId())) {
                    if (!AbilityDemonicRage.getDamagePlayers().contains(dealer.getUniqueId()))
                        AbilityDemonicRage.getDamagePlayers().add(dealer.getUniqueId());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> AbilityDemonicRage.getDamagePlayers().remove(dealer.getUniqueId()), 20 * 10);
                }

            }

            if (playerManager.getSpecies().name().equals("Wolf")) {
                AtomicBoolean isWolfNearby = new AtomicBoolean(false);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 50 * 50;
                players.forEach(all -> {
                    if (player.getWorld() == all.getWorld() && all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                        if (all != player) {
                            PlayerManager allManager = PlayerManager.get(all);
                            if (allManager.getSpecies().name().equals("Wolf")) {
                                isWolfNearby.set(true);
                            }
                        }
                    }
                });
                if (!isWolfNearby.get()) {
                    trueDamage *= 1.2f;
                }
            }

            if (EntityDamageListener.getTasks().containsKey(player.getUniqueId())) {
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

            if (playerManager.getShield() > 0) {

                if (damage < shield) {
                    trueDamage = 0;
                    player.sendTitle("", "§e⛨", 5, 10, 5);
                    player.playSound(player.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.MASTER, 100, 1f);
                    playerManager.setShield(shield - damage);
                } else {
                    trueDamage = damage - shield;
                    player.sendTitle("", "§c⛨ Break", 5, 10, 5);
                    player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 100, 1f);
                    playerManager.setShield(0);
                }

            }

            if (trueDamage >= 20) event.setDamage(0);
            else event.setDamage(trueDamage);
            playerManager.setHealth(health - trueDamage);

            player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getLocation().add(0, 0.5, 0), 50, Material.REDSTONE_BLOCK.createBlockData());

        }

    }

}
