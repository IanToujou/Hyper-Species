package net.toujoustudios.hyperspecies.ability.active.flora;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.List;

public class AbilityQuickGrowth extends Ability {

    public AbilityQuickGrowth() {
        super(
                "Quick Growth",
                List.of("§8Apply the bone meal effect to a plant", "§8that the user is looking at§8."),
                Element.FLORA,
                AbilityType.UTILITY,
                3,
                30,
                Material.BONE_MEAL,
                5,
                List.of("Elf", "Feline", "Wolf"),
                2,
                1
        );

    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 15);

        if (block.getType() == Material.WHEAT || block.getType() == Material.CARROTS || block.getType() == Material.BEETROOTS || block.getType() == Material.MELON_STEM || block.getType() == Material.PUMPKIN_STEM || block.getType() == Material.TORCHFLOWER_CROP || block.getType() == Material.PITCHER_CROP) {
            block.applyBoneMeal(BlockFace.UP);
            player.playSound(player.getLocation(), Sound.BLOCK_COMPOSTER_FILL_SUCCESS, SoundCategory.MASTER, 100, 1f);
            return true;
        }

        return false;

    }

}
