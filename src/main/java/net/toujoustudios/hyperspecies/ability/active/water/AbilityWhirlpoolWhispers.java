package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityWhirlpoolWhispers extends Ability {

    public AbilityWhirlpoolWhispers() {

        super(
                "Whirlpool Whispers",
                List.of("§8Summons a swirling vortex of water", "§8that disorients enemies for §d{duration}s§8."),
                Element.WATER,
                AbilityType.DEBUFF,
                6,
                180,
                Material.SNOWBALL,
                5,
                List.of("Aquatilia"),
                4,
                3,
                Objects.requireNonNull(Species.getSpecies("Aquatilia")).getSubSpecies("Siren")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 7, 9, 11, 13, 15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();
        new BukkitRunnable() {
            double y = 0;

            @Override
            public void run() {

                if (y >= 3) this.cancel();

                int radius = 2;
                double x = radius * Math.cos(y * 4);
                double z = radius * Math.sin(y * 4);

                for (int i = 0; i < 20; i++) {
                    player.getWorld().spawnParticle(Particle.DRIP_WATER, new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z), 4, 0.1, 0.1, 0.1);
                    y += 0.01;
                    x = radius * Math.cos(y * 4);
                    z = radius * Math.sin(y * 4);
                }

                player.getWorld().playSound(location, Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 0f);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 3 * 3;
                for (Player all : players) {
                    if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared)
                        all.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * duration, 0, false, false, true));
                }

            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 1);

        return true;
    }

}
