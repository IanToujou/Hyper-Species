package net.toujoustudios.hyperspecies.data.ability.active.fairy;

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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityStarwhisper extends Ability {

    public AbilityStarwhisper() {

        super(
                "Starwhisper",
                List.of("§8Creates a cloud, dealing " + Element.FAIRY.getEmoji() + " {damage}§8 to", "§8enemies. If allies pass the cloud,", "§8they will gain strength for §d{duration}s§8."),
                Element.FAIRY,
                Element.LIGHT,
                AbilityType.BUFF,
                5,
                180,
                Material.GOLD_NUGGET,
                8,
                List.of("Elf"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.DURATION, List.of(8,8,9,9,10,10,11,11,12));
        fields.put(AbilityField.DAMAGE, List.of(3,4,4,5,5,6,6,7,7));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3, 2f);
        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 100, 3, 0, 3);

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 5*5;
                players.forEach(all -> {
                    if(all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                        PlayerManager manager = PlayerManager.getPlayer(all);
                        location.getWorld().spawnParticle(Particle.CHERRY_LEAVES, location, 100, 3, 3, 3);
                        if(manager.hasTeam() && playerManager.hasTeam() && manager.getTeam() == playerManager.getTeam()) {
                            all.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*duration, 5, false, false, true));
                        } else if(all != player) all.damage(damage, player);
                    }
                });

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 2, 2);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20L * duration);

        return true;

    }

}
