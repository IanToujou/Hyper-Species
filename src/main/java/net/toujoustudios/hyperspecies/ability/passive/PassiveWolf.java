package net.toujoustudios.hyperspecies.ability.passive;

import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class PassiveWolf extends PassiveAbility {

    @Override
    public void execute(Player player) {

        if(player.hasPotionEffect(PotionEffectType.HUNGER)) player.removePotionEffect(PotionEffectType.HUNGER);

        if (player.isSneaking()) {

            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = 30 * 30;
            players.forEach(all -> {
                if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                    if (all != player)
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, SoundCategory.MASTER, 100, 2f);
                }
            });

        }

        if (player.getWorld().getTime() > 12500 && player.getWorld().getTime() < 23500) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 0, false, false, true));
        } else {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation().add(0, 1, 0));
            if (block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                if (!player.getWorld().getName().contains("farmworld")) {
                    // TODO: Remove in release
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0, false, false, true));
                }
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 0, false, false, true));
            }
        }

    }

}
