package net.toujoustudios.hyperspecies.ability.active.water;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityWaterDome extends Ability {

    public AbilityWaterDome() {

        super(
                "Water Dome",
                List.of("§8Creates a water dome that blocks all", "§8incoming damage during §d{duration}s§8."),
                Element.WATER,
                AbilityType.UTILITY,
                14,
                360,
                Material.HEART_OF_THE_SEA,
                5,
                List.of("Aquatilia"),
                7,
                5,
                Objects.requireNonNull(Species.getSpecies("Aquatilia")).getSubSpecies("Hydra")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, SoundCategory.MASTER, 2, 1.5f);
        ArrayList<Block> blocks = new ArrayList<>();

        Block center = player.getLocation().getBlock();
        int radius = 15;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if (center.getLocation().distance(b.getLocation()) <= radius) {
                        if (b.getType() == Material.AIR) {
                            b.setType(Material.BLUE_STAINED_GLASS);
                            blocks.add(b);
                        }
                    }
                }
            }
        }

        int hollowRadius = 13;
        for (int x = -hollowRadius; x <= hollowRadius; x++) {
            for (int y = -hollowRadius; y <= hollowRadius; y++) {
                for (int z = -hollowRadius; z <= hollowRadius; z++) {
                    Block b = center.getRelative(x, y, z);
                    if (center.getLocation().distance(b.getLocation()) <= hollowRadius) {
                        if (b.getType() == Material.BLUE_STAINED_GLASS) {
                            b.setType(Material.AIR);
                        }
                    }
                }
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_THUNDER, SoundCategory.MASTER, 2, 0f);
            blocks.forEach(b -> {
                if (b.getType() == Material.BLUE_STAINED_GLASS) b.setType(Material.AIR);
            });
        }, 20L * duration);

        return true;

    }

}
