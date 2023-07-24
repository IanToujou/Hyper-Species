package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityEarthboundThorns extends Ability {

    public AbilityEarthboundThorns() {

        super(
                "Earthbound Thorns",
                List.of("§8"),
                Element.EARTH,
                AbilityType.DAMAGE,
                8,
                180,
                Material.GOLDEN_SHOVEL,
                8,
                List.of("Dwarf"),
                4,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5,7,9,11,12,14));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));

        Location loc1 = player.getLocation().add(player.getLocation().getDirection().setY(0).multiply(3));
        Location loc2 = player.getLocation().add(player.getLocation().getDirection().setY(0).multiply(6));

        loc1.getWorld().spawnParticle(Particle.CRIT, loc1, 300, 2, 0.1, 2);
        loc2.getWorld().spawnParticle(Particle.CRIT, loc2, 300, 2, 0.1, 2);
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 6 * 6;
        for(Player all : players) {
            if(all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(loc1) <= radiusSquared) {
                if(all != player) all.damage(damage, player);
            } else if(all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(loc2) <= radiusSquared) {
                if(all != player) all.damage(damage, player);
            }
        }

        return true;
    }

}
