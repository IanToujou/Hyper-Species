package net.toujoustudios.hyperspecies.data.ability;

import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityMeteorStrike extends Ability {

    public AbilityMeteorStrike() {
        super("Meteor Strike", ItemList.DEMON_ABILITY_METEOR_STRIKE, 1, 1);
    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 50);
        if(block.getType() == Material.AIR) return false;
        Location impactLocation = block.getLocation();
        Location spawnLocation = impactLocation.add(0, 100, 0);

        player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 100, 0.2, 0.1, 0.2);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_DEATH, 3, 0.5f);

        final int[] times = {0};
        new BukkitRunnable() {

            @Override
            public void run() {
                player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 10, 0.2, 0.1, 0.2);
                if(times[0] >= 15) this.cancel();
                times[0]++;
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 0, 5);

        Fireball entity = (Fireball) player.getWorld().spawnEntity(spawnLocation, EntityType.FIREBALL);
        entity.setCustomName("Demon Meteor");
        entity.setCustomNameVisible(false);
        entity.setDirection(new Vector(0, -3, 0));
        entity.setGlowing(true);
        entity.setIsIncendiary(false);
        entity.setYield(0);

        return true;

    }

}
