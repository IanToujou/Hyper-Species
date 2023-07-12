package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class AbilityBurningRain extends Ability {

    public AbilityBurningRain() {

        super(
                "Burning Rain",
                List.of("§8Shoot a projectile that summons a", "§8cloud on hit, unleashing a burning", "§8rain that does " + Element.FIRE.getEmoji() + " §c{damage}§8 damage."),
                Element.FIRE,
                AbilityType.DAMAGE,
                6,
                80,
                Material.MAGMA_CREAM,
                5,
                List.of("Demon", "Reptile", "Human", "Feline", "Dwarf", "Wolf")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5,7,9,11,13,15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        Arrow arrow = player.launchProjectile(Arrow.class);



        return true;

    }

}
