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

public class AbilityHydroblast extends Ability {

    public AbilityHydroblast() {

        super(
                "Hydroblast",
                List.of("§8Shoots a concentrated jet of water", "§8at high velocity, dealing " + Element.WATER.getEmoji() + " {damage}§8 damage."),
                Element.WATER,
                AbilityType.DAMAGE,
                6,
                120,
                Material.ECHO_SHARD,
                5,
                List.of("Reptile", "Human", "Aquatilia"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(10, 12, 14, 16, 18, 20));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Vector direction = player.getLocation().getDirection();

        double space = 0.1;
        Location point1 = player.getLocation().add(0, 1, 0);
        Location point2 = player.getLocation().add(0, 1, 0).add(direction.multiply(12));
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
            world.spawnParticle(Particle.WATER_SPLASH, p1.getX(), p1.getY(), p1.getZ(), 100, 0.1, 0.1, 0.1);
            length += space;
            for (Player all : players) {
                if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(p1.toLocation(player.getWorld())) <= 1) {
                    if (all != player) {
                        all.damage(damage, player);
                        all.setVelocity(direction.setY(0.1).multiply(0.8f));
                    }
                }
            }
        }

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 2, 1.5f);
        player.getWorld().spawnParticle(Particle.WATER_WAKE, player.getLocation(), 100, 0.4, 0.4, 0.4);

        return true;

    }

}
