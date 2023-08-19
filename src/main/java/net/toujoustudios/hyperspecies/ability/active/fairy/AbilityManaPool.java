package net.toujoustudios.hyperspecies.ability.active.fairy;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityManaPool extends Ability {

    public AbilityManaPool() {

        super(
                "Mana Pool",
                List.of("§8Summons a mana pool that regenerates", "§8everyone's mana during §d{duration}s§8 at a rate", "§8of §d{rate}/s§8."),
                Element.FAIRY,
                AbilityType.BUFF,
                6,
                180,
                Material.LAPIS_LAZULI,
                8,
                List.of("Elf", "Angel", "Human"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10, 11, 12, 14));
        fields.put(AbilityField.RATE, List.of(1, 1, 2, 2, 2, 3, 3, 3, 4));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        int rate = getFieldValue(AbilityField.RATE, playerManager.getAbilityLevel(this));

        Location location = player.getLocation();
        assert location.getWorld() != null;
        player.getWorld().playSound(location, Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 3, 2f);

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 6 * 6;
                players.forEach(all -> {
                    if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                        PlayerManager manager = PlayerManager.get(all);
                        if (manager.getMana() < manager.getMaxMana()) manager.setMana(manager.getMana() + rate);
                        location.getWorld().spawnParticle(Particle.DRIP_WATER, location, 100, 5, 0.1, 5);
                    }
                });
            }

        }.runTaskTimer(HyperSpecies.getInstance(), 5, 5);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20L * duration);

        return true;

    }

}
