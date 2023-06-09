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

        // Regeneration in green biomes
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
            playerManager.setHealthRegeneration(0.2);
        } else {
            if(!playerManager.isRegenerationCoolingDown()) playerManager.setHealthRegeneration(0.2);
        }

        // Damage in cold biomes
        ArrayList<Biome> coldBiomes = new ArrayList<>();
        coldBiomes.add(Biome.SNOWY_TAIGA);
        coldBiomes.add(Biome.FROZEN_OCEAN);
        coldBiomes.add(Biome.FROZEN_PEAKS);
        coldBiomes.add(Biome.FROZEN_RIVER);
        coldBiomes.add(Biome.ICE_SPIKES);
        coldBiomes.add(Biome.SNOWY_BEACH);
        coldBiomes.add(Biome.SNOWY_PLAINS);
        coldBiomes.add(Biome.SNOWY_SLOPES);
        coldBiomes.add(Biome.SNOWY_TAIGA);

        if(coldBiomes.contains(player.getLocation().add(0, -1, 0).getBlock().getBiome())) {
            player.damage(1);
        }

        // Mana regeneration on leaf blocks
        ArrayList<Material> leafBlocks = new ArrayList<>();
        leafBlocks.add(Material.OAK_LEAVES);
        leafBlocks.add(Material.BIRCH_LEAVES);
        leafBlocks.add(Material.ACACIA_LEAVES);
        leafBlocks.add(Material.AZALEA_LEAVES);
        leafBlocks.add(Material.JUNGLE_LEAVES);
        leafBlocks.add(Material.MANGROVE_LEAVES);
        leafBlocks.add(Material.DARK_OAK_LEAVES);
        leafBlocks.add(Material.FLOWERING_AZALEA_LEAVES);
        leafBlocks.add(Material.SPRUCE_LEAVES);

        if(leafBlocks.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            playerManager.setManaRegeneration(0.3);
        } else playerManager.setManaRegeneration(0.1);

        // Strength on wood blocks
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

        if(woodBlocks.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 0, false, false, true));
        }

        // Weakness on granite blocks
        ArrayList<Material> graniteBlocks = new ArrayList<>();
        graniteBlocks.add(Material.GRANITE);
        graniteBlocks.add(Material.GRANITE_SLAB);
        graniteBlocks.add(Material.GRANITE_WALL);
        graniteBlocks.add(Material.GRANITE_STAIRS);
        graniteBlocks.add(Material.POLISHED_GRANITE);
        graniteBlocks.add(Material.POLISHED_GRANITE_SLAB);
        graniteBlocks.add(Material.POLISHED_GRANITE_STAIRS);

        if(graniteBlocks.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 10, 0, false, false, true));
        }

    }
}
