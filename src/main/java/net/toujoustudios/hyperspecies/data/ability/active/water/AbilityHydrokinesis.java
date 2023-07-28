package net.toujoustudios.hyperspecies.data.ability.active.water;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbilityHydrokinesis extends Ability {

    public AbilityHydrokinesis() {

        super(
                "Hydrokinesis",
                List.of("§8Manipulates water to create a barrier", "§8to block enemy attacks. The barrier", "§8stays active for §d{duration}s§8."),
                Element.WATER,
                AbilityType.TERRAIN,
                6,
                120,
                Material.CLAY_BALL,
                5,
                List.of("Human", "Aquatilia"),
                3,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 7, 9, 11, 13, 15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        Location location = player.getLocation();
        location.add(location.getDirection().multiply(5));
        Block block = new Location(location.getWorld(), location.getX(), player.getLocation().getY(), location.getZ()).getBlock();

        float y = player.getLocation().getYaw();
        if (y < 0) y += 360;
        y %= 360;
        int i = (int) ((y + 8) / 22.5);

        ArrayList<Block> blocks = new ArrayList<>();
        if (i >= 10 && i < 15) {
            // East
            for (int j = 0; j < 6; j++) {
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() - 2).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() - 1).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() + 1).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() + 2).getBlock());
            }
        } else if (i >= 7 && i < 10) {
            // North
            for (int j = 0; j < 6; j++) {
                blocks.add(new Location(block.getWorld(), block.getX() - 2, block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX() - 1, block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX() + 1, block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX() + 2, block.getY() + j, block.getZ()).getBlock());
            }
        } else if (i >= 3 && i < 7) {
            // West
            for (int j = 0; j < 6; j++) {
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() - 2).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() - 1).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() + 1).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ() + 2).getBlock());
            }
        } else {
            // South
            for (int j = 0; j < 6; j++) {
                blocks.add(new Location(block.getWorld(), block.getX() - 2, block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX() - 1, block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX(), block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX() + 1, block.getY() + j, block.getZ()).getBlock());
                blocks.add(new Location(block.getWorld(), block.getX() + 2, block.getY() + j, block.getZ()).getBlock());
            }
        }

        blocks.forEach(b -> {
            if (b.getType() == Material.AIR) b.setType(Material.BLUE_STAINED_GLASS);
        });

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 2, 1.5f);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            blocks.forEach(b -> {
                if (b.getType() == Material.BLUE_STAINED_GLASS) b.setType(Material.AIR);
            });
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 2, 0.5f);
        }, 20L * duration);

        return true;

    }
}
