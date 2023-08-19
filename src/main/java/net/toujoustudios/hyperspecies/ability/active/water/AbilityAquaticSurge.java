package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityAquaticSurge extends Ability {

    public AbilityAquaticSurge() {

        super(
                "Aquatic Surge",
                List.of("§8Summons a surge of water that knocks", "§8enemies away, dealing " + Element.WATER.getEmoji() + " {damage}§8 damage."),
                Element.WATER,
                AbilityType.DAMAGE,
                3,
                60,
                Material.PRISMARINE_CRYSTALS,
                5,
                List.of("Reptile", "Human", "Aquatilia"),
                2,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(3, 4, 5, 6, 7, 8));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Vector direction = player.getLocation().getDirection();

        double space = 0.1;
        Location point1 = player.getLocation().add(0, 1, 0);
        Location point2 = player.getLocation().add(0, 1, 0).add(direction.multiply(8));
        World world = point1.getWorld();
        assert world != null;

        if (point1.getWorld() != point2.getWorld()) return false;

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double distance = point1.distance(point2);
        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();
        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
        double length = 0;
        for (; length < distance; p1.add(vector)) {
            world.spawnParticle(Particle.FALLING_WATER, p1.getX(), p1.getY(), p1.getZ(), 50, 0.1, 0.1, 0.1);
            length += space;
            for (Player all : players) {
                if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(p1.toLocation(player.getWorld())) <= 1) {
                    if (all != player) {
                        all.damage(damage, player);
                        all.setVelocity(direction.setY(0.1).multiply(0.6f));
                    }
                }
            }
        }

        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 0.8f);
        player.getWorld().spawnParticle(Particle.WATER_WAKE, player.getLocation(), 100, 0.4, 0.4, 0.4);

        return true;

    }

}
