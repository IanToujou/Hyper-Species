package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbilityMeteorStrike extends Ability {

    public AbilityMeteorStrike() {
        super(
                "Meteor Strike",
                List.of("§8Summons a meteor that strikes from", "§8the sky, dealing " + Element.FIRE.getEmoji() + " {damage} §8in a range", "§8of §d{range}m§8."),
                Element.FIRE,
                AbilityType.DAMAGE,
                12,
                180,
                Material.FIRE_CHARGE,
                8,
                List.of("Demon")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.RANGE, List.of(8,9,10,12,14,16,18,20,24));
        fields.put(AbilityField.DAMAGE, List.of(10,12,14,16,18,20,24,28,30));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 50);
        if(block.getType() == Material.AIR) return false;
        Location impactLocation = block.getLocation();
        Location spawnLocation = impactLocation.add(0, 150, 0);

        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 100, 0.2, 0.1, 0.2);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 20, 0.2, 0.1, 0.2);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 3, 0.5f);

        final int[] times = {0};
        new BukkitRunnable() {

            @Override
            public void run() {
                player.getWorld().spawnParticle(Particle.CRIT, player.getLocation().add(0, 3*times[0], 0), 50, 0, 3, 0);
                if(times[0] >= 5) this.cancel();
                times[0]++;
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 0, 10);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {

            Fireball entity = (Fireball) player.getWorld().spawnEntity(spawnLocation, EntityType.FIREBALL);
            entity.setCustomName("Meteor Strike of " + player.getName());
            entity.setCustomNameVisible(false);
            entity.setDirection(new Vector(0, -3, 0));
            entity.setIsIncendiary(false);
            entity.setYield(0);

        }, 100);

        return true;

    }

}
