package net.toujoustudios.hyperspecies.data.ability.active.water;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AbilityAquaShield extends Ability {

    private static final ArrayList<UUID> activePlayers = new ArrayList<>();

    public AbilityAquaShield() {

        super(
                "Aqua Shield",
                List.of("§8Sneak to dash in the direction you", "§8are looking at. You can pass through", "§8walls and gain invisibility. This", "§8effect stays active for §d{duration}s§8."),
                Element.WATER,
                AbilityType.UTILITY,
                8,
                180,
                Material.HEART_OF_THE_SEA,
                5,
                List.of("Aquatilia"),
                5,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5,6,7,8,9,10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 1.5f);
        if(!activePlayers.contains(player.getUniqueId())) activePlayers.add(player.getUniqueId());

        BukkitTask task = new BukkitRunnable() {
            double phi = 0;
            @Override
            public void run() {
                Location location = player.getLocation();
                phi += Math.PI/10;
                for(double theta = 0; theta <= 2*Math.PI; theta += Math.PI/40) {
                    double r = 1.5;
                    double x = r*cos(theta)*sin(phi);
                    double y = r*cos(phi) + 1.5;
                    double z = r*sin(theta)*sin(phi);
                    location.add(x,y,z);
                    player.getWorld().spawnParticle(Particle.DRIP_WATER, location, 1, 0, 0, 0);
                    location.subtract(x,y,z);
                }
                if (phi > Math.PI) {
                    phi = 0;
                }
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 0, 1);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 2, 2f);
            activePlayers.remove(player.getUniqueId());
            task.cancel();
        }, 20L * duration);

        return true;

    }

    public static ArrayList<UUID> getActivePlayers() {
        return activePlayers;
    }

}
