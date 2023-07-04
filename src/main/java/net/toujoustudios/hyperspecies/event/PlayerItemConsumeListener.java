package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);

        List<Material> meat = List.of(
                Material.CHICKEN,
                Material.COOKED_CHICKEN,
                Material.BEEF,
                Material.COOKED_BEEF,
                Material.PORKCHOP,
                Material.COOKED_PORKCHOP,
                Material.RABBIT,
                Material.COOKED_RABBIT,
                Material.MUTTON,
                Material.COOKED_MUTTON,
                Material.ROTTEN_FLESH,
                Material.COD,
                Material.COOKED_COD,
                Material.SALMON,
                Material.COOKED_SALMON,
                Material.TROPICAL_FISH,
                Material.PUFFERFISH
        );

        if(playerManager.getSpecies().getName().equals("Elf")) {

            ItemStack item = event.getItem();
            Material material = item.getType();

            if(meat.contains(material)) {

                player.damage(5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 0, false, false, true));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                event.setCancelled(true);

            }

        } else if(playerManager.getSpecies().getName().equals("Feline")) {

            ItemStack item = event.getItem();
            Material material = item.getType();

            if(!meat.contains(material)) {

                player.damage(5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 0, false, false, true));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, SoundCategory.MASTER, 1, 1f);
                event.setCancelled(true);

            }

        }

    }

}
