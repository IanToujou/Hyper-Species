package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityHellblight extends Ability {

    public AbilityHellblight() {

        super(
                "Hellblight",
                List.of("§8Create a dark cloud that deals an", "§8AoE effect, slowing all enemies in", "§8a §d{range}m §8radius for §d{duration}s§8."),
                Element.FIRE,
                AbilityType.DEBUFF,
                4,
                60,
                Material.GUNPOWDER,
                8,
                List.of("Demon", "Reptile", "Human", "Feline", "Dwarf", "Wolf"),
                2,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.RANGE, List.of(8,10,12,15,18,20,24,28,30));
        fields.put(AbilityField.DURATION, List.of(5,6,7,8,10,12,15,18,20));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        int range = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));

        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 3, 1.5f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 500, 8, 0, 8);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = range*range;
        players.forEach(all -> {
            if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                if(all != player) all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*duration, 0, false, false, true));
            }
        });

        return true;

    }

}
