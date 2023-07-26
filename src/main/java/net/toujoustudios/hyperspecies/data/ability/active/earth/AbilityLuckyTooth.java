package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class AbilityLuckyTooth extends Ability {

    public AbilityLuckyTooth() {

        super(
                "Lucky Tooth",
                List.of("§8Grants luck for §d{duration}s§8."),
                Element.EARTH,
                AbilityType.UTILITY,
                10,
                300,
                Material.RAW_GOLD,
                8,
                List.of("Dwarf"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(20,30,40,50,60,70,80,90,100));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 20 * duration, 1, false, false, true));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100, 1f);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 100, 0.2, 0.2, 0.2);
        return true;
    }

}
