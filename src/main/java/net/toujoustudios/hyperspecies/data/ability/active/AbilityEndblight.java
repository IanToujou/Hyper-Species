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

public class AbilityEndblight extends Ability {

    public AbilityEndblight() {

        super(
                "Endblight",
                List.of("§8Blinds enemies in a radius of §d{range}m§8.", "§8for a duration of §d{duration}s."),
                Element.DARK,
                Element.FIRE,
                AbilityType.DEBUFF,
                5,
                60,
                Material.CHARCOAL,
                8,
                List.of("Demon"),
                3,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.RANGE, List.of(10,12,14,16,18,20,22,24,25));
        fields.put(AbilityField.DURATION, List.of(10,11,12,13,14,15,16,17,20));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        int range = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));

        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 3, 0f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 1000, 3, 0, 3);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = range*range;
        players.forEach(all -> {
            if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                if(all != player) all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*duration, 0, false, false, true));
            }
        });

        return true;

    }

}
