package net.toujoustudios.hyperspecies.data.ability.passive;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveAngel extends PassiveAbility {

    @Override
    public void execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if(player.isSneaking()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0, false, false, true));
        }

        if(player.getWorld().getTime() < 12500 && player.getWorld().getTime() > 23500 && !player.getWorld().isThundering() && !player.getWorld().hasStorm()) {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            if(block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 20, 0, false, false, true));
                playerManager.setHealthRegeneration(0.3);
            }
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 0, false, false, true));
        }

        if(player.getWorld().isThundering() || player.getWorld().hasStorm() || player.getLocation().getBlock().getType() == Material.WATER) {
            player.damage(1);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 0, false, false, true));
        }

    }

}
