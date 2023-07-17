package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityRayOfDoom extends Ability {

    public AbilityRayOfDoom() {

        super(
                "Ray Of Doom",
                List.of("§8Call in a ray from the sky, dealing", "§8continuous " + Element.FIRE.getEmoji() + " {damage} §8damage per second", "§8to enemies."),
                Element.FIRE,
                Element.DARK,
                AbilityType.DAMAGE,
                12,
                240,
                Material.ENDER_EYE,
                8,
                List.of("Demon", "Reptile", "Human"),
                7,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(1,2,3,4,5,6,7,8,10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 50);
        if(block.getType() == Material.AIR) return false;
        Location impactLocation = block.getLocation();

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));

        player.getWorld().spawnParticle(Particle.LAVA, impactLocation, 20, 0.1, 1, 0.1);
        player.getWorld().spawn(impactLocation, LightningStrike.class);
        player.getWorld().playSound(impactLocation, Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.MASTER, 3, 0.5f);
        player.getWorld().playSound(impactLocation, Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.MASTER, 3, 1.5f);
        player.getWorld().playSound(impactLocation, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.MASTER, 3, 0.5f);
        Location particleLocation = impactLocation.add(0, 10, 0);
        Location damageLocation = new Location(player.getWorld(), impactLocation.getX(), impactLocation.getY()-10, impactLocation.getZ());

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, particleLocation, 50, 1, 20, 1);
                player.getWorld().playSound(impactLocation, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.MASTER, 3, 1f);
                player.getWorld().playSound(impactLocation, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 3, 1f);

                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 6 * 6;
                players.forEach(all -> {
                    if(all.getLocation().distanceSquared(damageLocation) <= radiusSquared) {
                        all.damage(damage, player);
                    }
                });

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 20 * 5, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20 * 10);

        return true;

    }

}
