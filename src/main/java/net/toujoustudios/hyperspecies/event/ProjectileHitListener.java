package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        if(projectile.getType() == EntityType.FIREBALL && projectile.getName().startsWith("Meteor Strike of ")) {

            Player player = Bukkit.getPlayer(projectile.getName().split(" ")[3]);
            Ability ability = Ability.getAbility("Meteor Strike");

            if(player == null) return;
            if(ability == null) return;

            PlayerManager playerManager = PlayerManager.getPlayer(player);
            int xp = playerManager.getAbilityExperience(ability);
            int level = playerManager.getLevelFromExperience(xp);

            int damage = ability.getFieldValue(AbilityField.DAMAGE, level);
            int range = ability.getFieldValue(AbilityField.RANGE, level);

            Location location = projectile.getLocation();
            Block center = location.add(0, -2, 0).getBlock();
            ArrayList<Block> blocks = new ArrayList<>();
            int radius = 4;

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
            projectile.getWorld().spawnParticle(Particle.FLAME, location.add(0, -2, 0), 500, 4, 1, 4);

            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = range * range;
            players.forEach(all -> {
                if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                    all.damage(damage, projectile);
                }
            });


        }

    }

}
