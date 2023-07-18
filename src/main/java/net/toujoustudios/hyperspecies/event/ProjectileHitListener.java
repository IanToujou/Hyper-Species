package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity();

        if(projectile.getType() == EntityType.SPLASH_POTION && projectile.getName().startsWith("Molotov Cocktail")) {

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
                if(block.getType() == Material.AIR) block.setType(Material.FIRE);
            });

            projectile.getWorld().playSound(location, Sound.ITEM_FIRECHARGE_USE, SoundCategory.MASTER, 2, 0.5f);

        }

        if(projectile.getType() == EntityType.FIREBALL && projectile.getName().startsWith("Fireball of ")) {

            Player player = Bukkit.getPlayer(projectile.getName().split(" ")[2]);
            Ability ability = Ability.getAbility("Fireball");

            if(player == null) return;
            if(ability == null) return;

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            int xp = playerManager.getAbilityExperience(ability);
            int level = playerManager.getLevelFromExperience(xp);
            int damage = ability.getFieldValue(AbilityField.DAMAGE, level);

            Location location = projectile.getLocation();
            projectile.getWorld().spawnParticle(Particle.LAVA, location, 10, 0.1, 0.1, 0.1);

            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = 3 * 3;
            players.forEach(all -> {
                if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                    all.damage(damage, projectile);
                }
            });

        }

        if(projectile.getType() == EntityType.FIREBALL && projectile.getName().startsWith("Meteor Strike of ")) {

            Player player = Bukkit.getPlayer(projectile.getName().split(" ")[3]);
            Ability ability = Ability.getAbility("Meteor Strike");

            if(player == null) return;
            if(ability == null) return;

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            int damage = ability.getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(ability));
            int range = ability.getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(ability));

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
            projectile.getWorld().spawnParticle(Particle.FLAME, location, 200, 4, 1, 4);

            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = range * range;
            players.forEach(all -> {
                if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                    all.damage(damage, projectile);
                }
            });

            Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> blocks.forEach(block -> {
                if(block.getType() == Material.MAGMA_BLOCK) block.setType(Material.AIR);
            }), 20 * 10);

        }

        if(projectile.getType() == EntityType.FIREBALL && projectile.getName().startsWith("Total Annihilation of ")) {

            Player player = Bukkit.getPlayer(projectile.getName().split(" ")[3]);
            Ability ability = Ability.getAbility("Total Annihilation");

            if(player == null) return;
            if(ability == null) return;

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            int damage = ability.getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(ability));
            int grassRadius = ability.getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(ability));

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

            ArrayList<Block> grassBlocks = new ArrayList<>();

            for(int x = -grassRadius; x <= grassRadius; x++) {
                for(int y = -grassRadius; y <= grassRadius; y++) {
                    for(int z = -grassRadius; z <= grassRadius; z++) {
                        Block b = center.getRelative(x, y, z);
                        if(center.getLocation().distance(b.getLocation()) <= grassRadius) {
                            grassBlocks.add(b);
                        }
                    }
                }
            }

            blocks.forEach(block -> {
                if(block.getType() == Material.AIR) block.setType(Material.FIRE);
            });

            grassBlocks.forEach(block -> {
                if(block.getType() == Material.GRASS_BLOCK) {
                    int random = new Random().nextInt(3);
                    if(random == 0) block.setType(Material.SOUL_SAND);
                    else block.setType(Material.NETHERRACK);
                }
            });

            projectile.getWorld().spawnParticle(Particle.LAVA, location, 500, 2, 2, 2);
            projectile.getWorld().spawnParticle(Particle.SMOKE_LARGE, 500, 4, 1, 4);

            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = grassRadius * grassRadius;
            players.forEach(all -> {
                if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                    all.damage(damage, projectile);
                }
            });

            Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
               blocks.forEach(block -> {
                   if(block.getType() == Material.MAGMA_BLOCK) block.setType(Material.AIR);
               });
               grassBlocks.forEach(block -> {
                   if(block.getType() == Material.NETHERRACK || block.getType() == Material.SOUL_SAND) block.setType(Material.GRASS_BLOCK);
                   if(block.getType() == Material.FIRE) block.setType(Material.AIR);
               });
            }, 20 * 10);

        }

        if(projectile.getType() == EntityType.ARROW && projectile.getName().startsWith("Strike Of Corruption of ")) {

            Location location = event.getEntity().getLocation();
            Player player = Bukkit.getPlayer(projectile.getName().split(" ")[4]);
            Ability ability = Ability.getAbility("Strike Of Corruption");

            if(player == null) return;
            if(ability == null) return;

            PlayerManager playerManager = PlayerManager.getPlayer(player);
            projectile.getWorld().spawnEntity(location, EntityType.LIGHTNING);
            projectile.getWorld().spawnParticle(Particle.FLASH, location, 100, 0.1, 0.1, 0.1);

            int damage = ability.getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(ability));

            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = 5 * 5;
            players.forEach(all -> {
                if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                    all.damage(damage, player);
                }
            });

        }

    }

}
