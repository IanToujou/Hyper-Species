package net.toujoustudios.hyperspecies.data.ability.active;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class AbilityStrikingTrail extends Ability {

    public AbilityStrikingTrail() {

        super(
                "Striking Trail",
                List.of("§8Throws a fireball that destroys every", "§8bit of green in it's path, blinding", "§8nearby enemies for §d{duration}s§8."),
                Element.FIRE,
                AbilityType.TERRAIN,
                6,
                100,
                Material.RED_DYE,
                5,
                List.of("Demon", "Reptile", "Human"),
                List.of("")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5,6,7,8,9,10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setYield(0);
        fireball.customName(Component.text("Striking Trail of " + player.getName()));
        fireball.setCustomNameVisible(false);

        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        ArrayList<Block> grassBlocks = new ArrayList<>();
        ArrayList<Block> airBlocks = new ArrayList<>();

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {

                Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
                double radiusSquared = 6 * 6;
                players.forEach(all -> {
                    if(all.getLocation().distanceSquared(fireball.getLocation()) <= radiusSquared) {
                        all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 0, false, false, true));
                    }
                });

                Block center = fireball.getLocation().add(0, -1, 0).getBlock();
                ArrayList<Block> blocks = new ArrayList<>();
                int radius = 4;
                for(int x = -radius; x <= radius; x++) {
                    for(int y = -radius; y <= radius; y++) {
                        for(int z = -radius; z <= radius; z++) {
                            Block b = center.getRelative(x, y, z);
                            if(center.getLocation().distance(b.getLocation()) <= radius) {
                                int random = new Random().nextInt(6);
                                blocks.add(b);
                                if(random == 0) {
                                    Block fire = b.getLocation().getBlock();
                                    if(fire.getType() == Material.AIR) {
                                        fire.setType(Material.FIRE);
                                        airBlocks.add(fire);
                                    }
                                }
                            }
                        }
                    }
                }

                blocks.forEach(block -> {
                    if(block.getType() == Material.GRASS_BLOCK) {
                        block.setType(Material.NETHERRACK);
                        grassBlocks.add(block);
                    }
                });

            }

        }.runTaskTimer(HyperSpecies.getInstance(), 3, 3);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            task.cancel();
            grassBlocks.forEach(block -> block.setType(Material.GRASS_BLOCK));
            airBlocks.forEach(block -> block.setType(Material.AIR));
        }, 20 * 3);

        return true;

    }

}
