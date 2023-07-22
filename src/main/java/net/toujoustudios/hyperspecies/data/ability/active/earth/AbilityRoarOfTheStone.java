package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityRoarOfTheStone extends Ability {

    public AbilityRoarOfTheStone() {

        super(
                "Roar Of The Stone",
                List.of("§8Creates a shockwave in a radius of §d{range}m§8,", "§8knocking enemies away and slowing", "§8them for §d{duration}s§8."),
                Element.EARTH,
                AbilityType.DEBUFF,
                6,
                140,
                Material.STONE_SHOVEL,
                8,
                List.of("Reptile", "Dwarf", "Wolf"),
                3,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5,6,7,8,9,10,11,12,15));
        fields.put(AbilityField.RANGE, List.of(7,8,9,10,11,12,13,14,15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        int range = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHERRY_SAPLING_PLACE, SoundCategory.MASTER, 3, 0f);
        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getLocation(), 100, 1, 0, 1, Material.DIRT.createBlockData());

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = range * range;
        players.forEach(all -> {
            if(all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                if(all != player) {
                    Vector unitVector = all.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                    all.setVelocity(new Vector(unitVector.getX(), 0.6f, unitVector.getZ()).multiply(1.2f));
                    all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration * 20, 1, false, false, true));
                }
            }
        });

        return true;

    }

}
