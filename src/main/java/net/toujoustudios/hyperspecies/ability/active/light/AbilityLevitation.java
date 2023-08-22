package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class AbilityLevitation extends Ability {

    public AbilityLevitation() {

        super(
                "Levitation",
                List.of("§8Gain levitation for a duration of", "§d{duration}s§8."),
                Element.LIGHT,
                AbilityType.UTILITY,
                6,
                90,
                Material.FEATHER,
                8,
                List.of("Elf", "Angel"),
                2,
                2,
                null
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 7, 9, 11, 13, 15, 17, 19, 20));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * duration, 0, false, false, true));
        return true;
    }

}
