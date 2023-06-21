package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class AbilityStrikingTrail extends Ability {

    public AbilityStrikingTrail() {

        super(
                "Striking Trail",
                List.of("§8Throws a fireball that destroys every", "§8bit of green in it's path, blinding", "§8nearby enemies for §d{duration}s§8."),
                Element.FIRE,
                AbilityType.DEBUFF,
                6,
                100,
                Material.MAGMA_CREAM,
                5,
                List.of("Demon", "Reptile", "Human")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5,7,9,11,13,15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        return false;
    }

}
