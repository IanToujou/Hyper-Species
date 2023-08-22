package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityBlindingLight extends Ability {

    public AbilityBlindingLight() {

        super(
                "Blinding Light",
                List.of("§8Blind enemies in a range of §d{range}m", "§8for §d{duration}s§8."),
                Element.LIGHT,
                AbilityType.DEBUFF,
                6,
                120,
                Material.FEATHER,
                5,
                List.of("Elf", "Angel"),
                3,
                2,
                null
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.RANGE, List.of(6, 7, 8, 9, 10, 11));
        fields.put(AbilityField.DURATION, List.of(3, 3, 4, 4, 5, 5));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.get(player);
        int range = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.getWorld().spawnParticle(Particle.FLASH, player.getLocation(), 5, 0, 0, 0);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 3, 2f);
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = range * range;
        players.forEach(all -> {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                if (all != player)
                    all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * duration, 0, false, false, true));
            }
        });
        return true;
    }

}
