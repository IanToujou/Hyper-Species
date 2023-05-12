package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player player) {

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            double damage = event.getDamage();
            double health = playerManager.getHealth();
            double shield = playerManager.getShield();
            double trueDamage = damage;

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
