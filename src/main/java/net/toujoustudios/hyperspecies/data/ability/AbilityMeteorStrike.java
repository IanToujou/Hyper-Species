package net.toujoustudios.hyperspecies.data.ability;

import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.entity.Player;

public class AbilityMeteorStrike extends Ability {

    public AbilityMeteorStrike() {
        super("Meteor Strike", ItemList.DEMON_ABILITY_METEOR_STRIKE, 1, 1);
    }

    @Override
    public void execute(Player player) {
        player.sendMessage("Ability used: " + getName());
    }

}
