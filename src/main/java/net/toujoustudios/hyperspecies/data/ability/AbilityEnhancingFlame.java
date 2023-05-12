package net.toujoustudios.hyperspecies.data.ability;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityEnhancingFlame extends Ability {

    public AbilityEnhancingFlame() {
        super("Enhancing Flame", ItemList.DEMON_ABILITY_ENHANCING_FLAME, 5, 90);
    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        Location location = player.getLocation();
        Block block = location.getBlock();

        player.getWorld().playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 3, 0.8f);
        player.getWorld().spawnParticle(Particle.FLAME, location, 300, 0.3, 0, 0.3);
        player.getWorld().spawnParticle(Particle.LAVA, location, 50, 0, 2, 0);

        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*60, 0, false, false, true));

        if(block.getType() == Material.AIR) {
            player.getWorld().getBlockAt(player.getLocation()).setType(Material.FIRE);
        }

        return true;

    }

}
