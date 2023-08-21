package net.toujoustudios.hyperspecies.ability.active.electro;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AbilityLightningStorm extends Ability {

    public AbilityLightningStorm() {

        super(
                "Lightning Storm",
                List.of("§8Summons a lightning storm that strikes", "§8lightning bolts, dealing " + Element.ELECTRO.getEmoji() + " {damage}§8 AoE", "§8damage. The storm lasts §d{duration}s§8."),
                Element.ELECTRO,
                Element.WATER,
                AbilityType.DAMAGE,
                15,
                600,
                Material.GLOW_INK_SAC,
                5,
                List.of("Reptile", "Aquatilia"),
                8,
                5
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(3, 4, 5, 6, 7, 8));
        fields.put(AbilityField.DURATION, List.of(20, 25, 30, 35, 40, 45));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();
        assert location.getWorld() != null;

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        player.getWorld().setStorm(true);

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                int randomX = new Random().nextInt(101) - 50;
                int randomZ = new Random().nextInt(101) - 50;
                int x = (int) (randomX + location.getX());
                int z = (int) (randomZ + location.getZ());

                Location loc = new Location(location.getWorld(), x, location.getWorld().getHighestBlockYAt(x, z), z);
                assert loc.getWorld() != null;
                loc.getWorld().strikeLightningEffect(loc);

                for (Player all : players) {
                    if (all.getWorld() == loc.getWorld() && all.getLocation().distanceSquared(loc) <= 4) {
                        if (all != player) all.damage(damage, player);
                    }
                }

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 20 * 5, 10);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            player.getWorld().setStorm(false);
        }, duration * 20L);

        return true;

    }

}
