package net.toujoustudios.hyperspecies.ability.active.air;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityWindShock extends Ability {

    public AbilityWindShock() {

        super(
                "Wind Shock",
                List.of("§8Knocks back the target and deals " + Element.AIR.getEmoji() + " {damage}", "§8by using compressed air."),
                Element.AIR,
                AbilityType.DAMAGE,
                5,
                80,
                Material.TNT,
                5,
                List.of("Angel", "Feline", "Wolf"),
                3,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 10);
        Location location = block.getLocation();
        Vector direction = player.getLocation().getDirection();

        player.getWorld().spawnParticle(Particle.CLOUD, location, 200, 0.3, 0.3, 0.3);
        player.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.MASTER, 5, 2f);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 4 * 4;
        for (Player all : players) {
            if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared) {
                all.setVelocity(new Vector(direction.getX(), 1, direction.getY()).multiply(1.3f));
                all.damage(damage, player);
            }
        }

        return true;

    }

}
