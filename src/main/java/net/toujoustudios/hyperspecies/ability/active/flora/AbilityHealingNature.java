package net.toujoustudios.hyperspecies.ability.active.flora;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityHealingNature extends Ability {

    public AbilityHealingNature() {

        super(
                "Healing Nature",
                List.of("§8Slowly regenerates an ally until the", "§8total regenerated amount reaches §c❤ {heal}§8."),
                Element.FLORA,
                AbilityType.HEAL,
                4,
                50,
                Material.GLOW_BERRIES,
                5,
                List.of("Elf", "Angel", "Human", "Aquatilia", "Feline"),
                1,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.HEAL, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int heal = getFieldValue(AbilityField.HEAL, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 50);

        Player target = null;
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 5 * 5;
        for (Player all : players) {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared)
                target = all;
        }
        if (target == null) return false;

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 2, 1f);
        target.getWorld().spawnParticle(Particle.HEART, target.getLocation().add(0, 3, 0), 50, 0.3, 0.1, 0.3);

        Player finalTarget = target;
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                PlayerManager manager = PlayerManager.get(finalTarget);
                manager.setHealth(manager.getHealth() + heal / 10f);
            }
        }.runTaskTimer(HyperSpecies.getInstance(), 0, 20);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), task::cancel, 20 * 10);

        return true;

    }

}
