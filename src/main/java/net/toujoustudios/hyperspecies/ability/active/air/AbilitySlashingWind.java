package net.toujoustudios.hyperspecies.ability.active.air;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilitySlashingWind extends Ability {

    public AbilitySlashingWind() {

        super(
                "Slashing Wind",
                List.of("§8Throw slashing strikes through the air,", "§8dealing " + Element.AIR.getEmoji() + " {damage}§8 to enemies."),
                Element.AIR,
                AbilityType.DAMAGE,
                8,
                180,
                Material.RABBIT_FOOT,
                8,
                List.of("Angel", "Feline", "Wolf"),
                4,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 6, 7, 8, 9, 10, 12, 14, 15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        new BukkitRunnable() {
            double y = 0;

            @Override
            public void run() {

                if (y >= 1) this.cancel();

                int radius = 3;
                double x = radius * Math.cos(y * 8);
                double z = radius * Math.sin(y * 8);

                for (int i = 0; i < 20; i++) {
                    player.getWorld().spawnParticle(Particle.CLOUD, new Location(location.getWorld(), location.getX() + x, location.getY() + y, location.getZ() + z), 4, 0.1, 0.1, 0.1);
                    y += 0.01;
                    x = radius * Math.cos(y * 8);
                    z = radius * Math.sin(y * 8);
                }

                player.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.MASTER, 2, 0f);
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 3 * 3;
                for (Player all : players) {
                    if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                        all.damage(damage, player);
                    }
                }

            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 1);

        return true;

    }

}
