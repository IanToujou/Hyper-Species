package net.toujoustudios.hyperspecies.data.ability;

import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.entity.Player;

public class AbilityDemonicRage extends Ability {


    public AbilityDemonicRage() {
        super("Demonic Rage", ItemList.DEMON_ABILITY_DEMONIC_RAGE, 10, 120);
    }

    @Override
    public boolean execute(Player player) {
        return true;
    }

}
