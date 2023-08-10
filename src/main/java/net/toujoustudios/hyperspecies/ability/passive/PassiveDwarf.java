package net.toujoustudios.hyperspecies.ability.passive;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PassiveDwarf extends PassiveAbility {

    @Override
    public void execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10, 1, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 0, false, false, true));

        if (playerManager.isDrunk()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 5, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 1, false, false, true));
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 5, 0, false, false, true));
        }

        if (player.getLocation().getBlock().getType() == Material.WATER) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 5, 2, false, false, true));
        }

    }

}
