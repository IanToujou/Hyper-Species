package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class AbilityStoneBorn extends Ability {

    public AbilityStoneBorn() {

        super(
                "Stone Born",
                List.of("§8Use the power of the earth to gain", "§8a resistance buff during §d{duration}s§8."),
                Element.EARTH,
                AbilityType.BUFF,
                4,
                90,
                Material.RAW_IRON,
                8,
                List.of("Reptile", "Human", "Dwarf", "Wolf"),
                2,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10, 12, 14, 16, 18, 20, 22, 24, 26));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * duration, 1, false, false, true));
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT, SoundCategory.MASTER, 1, 1f);
        return true;
    }

}
