package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityTorrentialRain extends Ability {

    public AbilityTorrentialRain() {

        super(
                "Torrential Rain",
                List.of("§8Summons a downpour of heavy rain,", "§8soaking enemies and reducing their", "§8movement for §d{duration}s§8."),
                Element.WATER,
                AbilityType.DEBUFF,
                4,
                120,
                Material.CLAY_BALL,
                8,
                List.of("Reptile", "Angel", "Human", "Aquatilia"),
                2,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10, 11, 12, 13, 14, 15, 16, 17, 18));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        location.getWorld().spawnParticle(Particle.DRIP_WATER, location.add(0, 4, 0), 500, 2, 0.1, 2);
        location.getWorld().playSound(location, Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 0.5f);
        location.subtract(0, 4, 0);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            location.getWorld().playSound(location, Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 1.5f);
            Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
            double radiusSquared = 5 * 5;
            for (Player all : players) {
                if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                    all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * duration, 2, false, false, true));
                }
            }
        }, 20 * 2);

        return true;

    }

}
