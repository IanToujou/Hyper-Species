package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class AbilityMagmaticDetonation extends Ability {

    public AbilityMagmaticDetonation() {
        super(
                "Magmatic Detonation",
                List.of("§8The user spontaneously explodes, unleashing", "§8a shockwave, releasing magma blocks", "§8that deal " + Element.FIRE.getEmoji() + " {damage}§8 to players on impact."),
                Element.FIRE,
                Element.EARTH,
                AbilityType.DAMAGE,
                14,
                360,
                Material.MAGMA_BLOCK,
                8,
                List.of("Demon", "Reptile"),
                8,
                5
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(10,12,14,16,18,20,22,24,26));
        setFields(fields);
    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));

        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 5, 0, 1, 0);
        player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 50, 0.1, 0.5, 0.1);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 3, 0.2f);
        ArrayList<FallingBlock> blocks = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            int x = new Random().nextInt(40)-20;
            int z = new Random().nextInt(40)-20;
            FallingBlock block = player.getWorld().spawnFallingBlock(player.getLocation().add(0, 3, 0), Material.MAGMA_BLOCK.createBlockData());
            block.setVelocity(new Vector((double) x/10, 1, (double)z/10));
            block.setDropItem(false);
            block.setInvulnerable(true);
            block.setCancelDrop(true);
            blocks.add(block);
        }

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                blocks.forEach(b -> {
                    if(b.getLocation().add(0, -2, 0).getBlock().getType() != Material.AIR) {
                        b.remove();
                        b.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, b.getLocation(), 5, 0, 0.1, 0);
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 3, 1f);
                        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                        double radiusSquared = 8 * 8;
                        players.forEach(all -> {
                            if(all.getLocation().distanceSquared(b.getLocation()) <= radiusSquared) {
                                if(all != player) all.damage(damage, player);
                            }
                        });
                    }
                });

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 2, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            blocks.forEach(Entity::remove);
        }, 20 * 4);

        return true;

    }

}
