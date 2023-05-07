package net.toujoustudios.hyperspecies.data.ability;

import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AbilityMeteorStrike extends Ability {

    public AbilityMeteorStrike() {
        super("Meteor Strike", ItemList.DEMON_ABILITY_METEOR_STRIKE, 1, 1);
    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 50);
        if(block.getType() == Material.AIR) return false;
        Location spawnLocation = block.getLocation().add(0, 100, 0);

        Fireball entity = (Fireball) player.getWorld().spawnEntity(spawnLocation, EntityType.FIREBALL);
        entity.setDirection(new Vector(0, -1, 0));
        entity.setGlowing(true);
        entity.setIsIncendiary(false);
        entity.setYield(0);

        return true;

    }

}
