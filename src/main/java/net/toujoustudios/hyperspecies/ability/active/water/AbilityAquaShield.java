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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AbilityAquaShield extends Ability {

    public AbilityAquaShield() {

        super(
                "Aqua Shield",
                List.of("§8Forms a protective shield of water", "§8that provides §e⛨ {shield}§8."),
                Element.WATER,
                AbilityType.UTILITY,
                8,
                180,
                Material.NAUTILUS_SHELL,
                5,
                List.of("Aquatilia"),
                5,
                4,
                Objects.requireNonNull(Species.getSpecies("Aquatilia")).getSubSpecies("Hydra")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.SHIELD, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int shield = getFieldValue(AbilityField.SHIELD, playerManager.getAbilityLevel(this));
        player.playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 1.5f);

        new BukkitRunnable() {
            double phi = 0;

            @Override
            public void run() {
                Location location = player.getLocation();
                phi += Math.PI / 10;
                for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 40) {
                    double r = 1.5;
                    double x = r * cos(theta) * sin(phi);
                    double y = r * cos(phi) + 1.5;
                    double z = r * sin(theta) * sin(phi);
                    location.add(x, y, z);
                    player.getWorld().spawnParticle(Particle.DRIP_WATER, location, 1, 0, 0, 0);
                    location.subtract(x, y, z);
                }
                if (phi > Math.PI) {
                    this.cancel();
                }
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 0, 1);

        playerManager.setShield(playerManager.getShield() + shield);

        return true;

    }

}
