package net.toujoustudios.hyperspecies.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        if(projectile.getType() == EntityType.FIREBALL && projectile.getName().equals("Demon Meteor")) {

            Location location = projectile.getLocation();
            Block center = location.getBlock();
            ArrayList<Block> blocks = new ArrayList<>();
            int radius = 2;

            for(int x = -radius; x <= radius; x++) {
                for(int y = -radius; y <= radius; y++) {
                    for(int z = -radius; z <= radius; z++) {
                        Block b = center.getRelative(x, y, z);
                        if(center.getLocation().distance(b.getLocation()) <= radius) {
                            blocks.add(b);
                        }
                    }
                }
            }

            blocks.forEach(block -> {
                if(block.getType() == Material.AIR) block.setType(Material.MAGMA_BLOCK);
            });

            projectile.getWorld().spawnParticle(Particle.LAVA, location, 500, 2, 2, 2);

        }

    }

}
