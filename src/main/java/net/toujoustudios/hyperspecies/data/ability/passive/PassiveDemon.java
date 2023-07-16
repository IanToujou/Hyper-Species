package net.toujoustudios.hyperspecies.data.ability.passive;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PassiveDemon extends PassiveAbility {

    @Override
    public void execute(Player player) {

        // Permanent fire resistance
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 20, 0, false, false, true));

        // Vision and speed at night
        // Damage in sunlight
        if(player.getWorld().getTime() > 12500 && player.getWorld().getTime() < 23500) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 20, 0, false, false, true));
        } else {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            if(block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                player.damage(2);
            }
        }

        // Slowness and weakness in water
        if(player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 1, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 1, false, false, true));
        } else if(player.getWorld().hasStorm() || player.getWorld().isThundering()) {
            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            if(block.getType() == Material.AIR || block.getLocation().getY() < player.getLocation().getY()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 1, false, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 1, false, false, true));
            }
        }

        List<Material> demonBlocks = List.of(Material.NETHERRACK, Material.NETHER_BRICKS);

        // Strength on nether rack
        if(demonBlocks.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 1, false, false, true));
        }

        // Mana drain on soul sand
        if(player.getLocation().add(0, -0.8, 0).getBlock().getType() == Material.SOUL_SAND) {
            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.setMana(playerManager.getMana() - 0.5);
        } else {
            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.setManaRegeneration(0.1);
        }

    }

}
