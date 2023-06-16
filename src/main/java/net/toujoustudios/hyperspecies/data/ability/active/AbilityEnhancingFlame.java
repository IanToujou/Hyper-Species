package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AbilityEnhancingFlame extends Ability {

    public AbilityEnhancingFlame() {
        super("Enhancing Flame", List.of("§8Enhances the users weapons, allowing", "§8them to deal fire damage and potentially", "§8setting the enemies on fire."), Element.FIRE, 5, 90, Material.BLAZE_POWDER);
    }

    @Override
    public boolean execute(Player player) {

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
