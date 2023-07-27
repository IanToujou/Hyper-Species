package net.toujoustudios.hyperspecies.data.ability.active.electro;

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
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

public class AbilitySilentDash extends Ability {

    public AbilitySilentDash() {

        super(
                "Silent Dash",
                List.of("§8Sneak to dash in the direction you", "§8are looking at. You can pass through", "§8walls and gain invisibility. This", "§8effect stays active for §d{duration}s§8."),
                Element.ELECTRO,
                Element.LIGHT,
                AbilityType.UTILITY,
                9,
                240,
                Material.STONE,
                5,
                List.of("Dwarf"),
                6,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10,12,14,16,18,20));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        final int[] remaining = {3};

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                if(remaining[0] <= 0) {
                    this.cancel();
                    return;
                }

                if(player.isSneaking()) {

                    Location location = player.getLocation();
                    Vector direction = location.getDirection();
                    direction.normalize();
                    direction.multiply(5);
                    location.add(direction);
                    if(location.getBlock().getType() == Material.AIR) {
                        player.teleport(new Location(location.getWorld(), location.getX(), player.getLocation().getY(), location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 100, 0.5, 0.5, 0.5);
                        player.setVelocity(player.getLocation().getDirection());
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 10, 0, false, false, true));
                        remaining[0]--;
                    }

                }

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 10, 10);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            if(!task.isCancelled()) task.cancel();
        }, 20L * duration);

        return true;
    }

}
