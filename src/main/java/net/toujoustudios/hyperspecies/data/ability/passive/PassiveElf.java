package net.toujoustudios.hyperspecies.data.ability.passive;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PassiveElf extends PassiveAbility{
    @Override
    public void execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        ArrayList<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.FOREST);
        biomes.add(Biome.FLOWER_FOREST);
        biomes.add(Biome.BIRCH_FOREST);
        biomes.add(Biome.DARK_FOREST);
        biomes.add(Biome.BIRCH_FOREST);
        biomes.add(Biome.OLD_GROWTH_BIRCH_FOREST);
        biomes.add(Biome.JUNGLE);
        biomes.add(Biome.BAMBOO_JUNGLE);
        biomes.add(Biome.SPARSE_JUNGLE);

        if(biomes.contains(player.getLocation().getBlock().getBiome())) {
            playerManager.setHealthRegeneration(0.5);
        }

        ArrayList<Material> dioriteBlocks = new ArrayList<>();
        dioriteBlocks.add(Material.DIORITE);
        dioriteBlocks.add(Material.DIORITE_SLAB);
        dioriteBlocks.add(Material.DIORITE_WALL);
        dioriteBlocks.add(Material.DIORITE_STAIRS);
        dioriteBlocks.add(Material.POLISHED_DIORITE);
        dioriteBlocks.add(Material.POLISHED_DIORITE_SLAB);
        dioriteBlocks.add(Material.POLISHED_DIORITE_STAIRS);

        if(dioriteBlocks.contains(player.getLocation().getBlock().getType())) {
            playerManager.setManaRegeneration(0.5);
        }

        ArrayList<Material> woodBlocks = new ArrayList<>();
        woodBlocks.add(Material.OAK_WOOD);
        woodBlocks.add(Material.OAK_PLANKS);
        woodBlocks.add(Material.BIRCH_WOOD);
        woodBlocks.add(Material.BIRCH_PLANKS);
        woodBlocks.add(Material.ACACIA_WOOD);
        woodBlocks.add(Material.ACACIA_PLANKS);
        woodBlocks.add(Material.DARK_OAK_WOOD);
        woodBlocks.add(Material.DARK_OAK_PLANKS);
        woodBlocks.add(Material.JUNGLE_WOOD);
        woodBlocks.add(Material.JUNGLE_PLANKS);
        woodBlocks.add(Material.SPRUCE_WOOD);
        woodBlocks.add(Material.SPRUCE_PLANKS);
        woodBlocks.add(Material.MANGROVE_WOOD);
        woodBlocks.add(Material.MANGROVE_PLANKS);
        woodBlocks.add(Material.WARPED_STEM);
        woodBlocks.add(Material.WARPED_PLANKS);
        woodBlocks.add(Material.CRIMSON_STEM);
        woodBlocks.add(Material.CRIMSON_PLANKS);

        if(woodBlocks.contains(player.getLocation().getBlock().getType())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 0, false, false, true));
        }

    }
}
