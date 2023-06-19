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

import java.util.HashMap;
import java.util.List;

public class AbilityEnhancingFlame extends Ability {

    public AbilityEnhancingFlame() {

        super(
                "Enhancing Flame",
                List.of("§8Enhances the users weapons, allowing", "§8them to deal " + Element.FIRE.getFullName() + " §8damage and ", "§8setting enemies on fire for §d{duration}s§8."),
                Element.FIRE,
                AbilityType.BUFF,
                5,
                90,
                Material.BLAZE_POWDER,
                8,
                List.of("Demon", "Elf")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(1,3,5,8,10,12,15,18,20));

        setFields(fields);

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
