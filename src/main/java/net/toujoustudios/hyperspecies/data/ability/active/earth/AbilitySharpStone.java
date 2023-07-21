package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class AbilitySharpStone extends Ability {

    public AbilitySharpStone() {

        super(
                "Sharp Stone",
                List.of("§8Shoot a projectile that penetrates", "§8armor, dealing " + Element.EARTH + " {damage}§8 true damage."),
                Element.EARTH,
                AbilityType.DAMAGE,
                4,
                90,
                Material.IRON_NUGGET,
                8,
                List.of("Reptile", "Dwarf"),
                2,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5,6,7,8,9,10,11,12,13));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, SoundCategory.MASTER, 1, 0.5f);
        Arrow arrow = player.launchProjectile(Arrow.class);
        arrow.setDamage(0);
        arrow.customName(Component.text("Sharp Stone of " + player.getName()));
        arrow.setCustomNameVisible(false);
        return true;
    }

}
