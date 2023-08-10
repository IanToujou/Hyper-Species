package net.toujoustudios.hyperspecies.ability.active.fire;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
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

public class AbilityFlamingBlight extends Ability {

    public AbilityFlamingBlight() {
        super(
                "Flaming Blight",
                List.of("§8Create a flaming cloud that deals", Element.FIRE.getEmoji() + " {damage}§8 to all enemies in a §d{range}m §8radius", "§8slowing them for §d{duration}s§8."),
                Element.FIRE,
                AbilityType.DEBUFF,
                5,
                70,
                Material.GLOWSTONE,
                8,
                List.of("Demon", "Reptile", "Wolf"),
                3,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.RANGE, List.of(8, 10, 12, 15, 18, 20, 24, 28, 30));
        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 10, 12, 15, 18, 20));
        fields.put(AbilityField.DAMAGE, List.of(2, 3, 4, 5, 6, 7, 8, 9, 10));

        setFields(fields);
    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int xp = playerManager.getAbilityExperience(this);
        int level = playerManager.getLevelFromExperience(xp);

        int duration = getFieldValue(AbilityField.DURATION, level);
        int range = getFieldValue(AbilityField.RANGE, level);
        int damage = getFieldValue(AbilityField.DAMAGE, level);

        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 3, 0.5f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 500, 8, 0, 8);
        player.getWorld().spawnParticle(Particle.SMALL_FLAME, player.getLocation(), 300, 8, 0, 8);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = range * range;
        players.forEach(all -> {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                if (all != player) {
                    all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * duration, 0, false, false, true));
                    all.damage(damage);
                }
            }
        });

        return true;

    }

}
