package net.toujoustudios.hyperspecies.ability.passive;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PassiveElf extends PassiveAbility {
    @Override
    public void execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);

        // Regeneration in green biomes
        ArrayList<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.FOREST);
        biomes.add(Biome.FLOWER_FOREST);
        biomes.add(Biome.BIRCH_FOREST);
        biomes.add(Biome.DARK_FOREST);
        biomes.add(Biome.BIRCH_FOREST);
        biomes.add(Biome.TAIGA);
        biomes.add(Biome.OLD_GROWTH_BIRCH_FOREST);
        biomes.add(Biome.JUNGLE);
        biomes.add(Biome.BAMBOO_JUNGLE);
        biomes.add(Biome.SPARSE_JUNGLE);
        biomes.add(Biome.WARPED_FOREST);
        biomes.add(Biome.CRIMSON_FOREST);

        if (biomes.contains(player.getLocation().getBlock().getBiome())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 0, false, false, true));
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

        if (coldBiomes.contains(player.getLocation().add(0, -1, 0).getBlock().getBiome())) {
            if (!player.getWorld().getName().contains("farmworld")) {
                // TODO: Remove in release
                player.damage(1);
            }
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

        if (leafBlocks.contains(player.getLocation().subtract(0, 1, 0).getBlock().getType()) || leafBlocks.contains(player.getLocation().getBlock().getType())) {
            playerManager.setManaRegeneration(0.3);
        } else playerManager.setManaRegeneration(0.1);

        // Strength on wood blocks
        ArrayList<Material> woodBlocks = new ArrayList<>();
        woodBlocks.add(Material.OAK_LOG);
        woodBlocks.add(Material.OAK_PLANKS);
        woodBlocks.add(Material.BIRCH_LOG);
        woodBlocks.add(Material.BIRCH_PLANKS);
        woodBlocks.add(Material.ACACIA_LOG);
        woodBlocks.add(Material.ACACIA_PLANKS);
        woodBlocks.add(Material.DARK_OAK_LOG);
        woodBlocks.add(Material.DARK_OAK_PLANKS);
        woodBlocks.add(Material.JUNGLE_LOG);
        woodBlocks.add(Material.JUNGLE_PLANKS);
        woodBlocks.add(Material.SPRUCE_LOG);
        woodBlocks.add(Material.SPRUCE_PLANKS);
        woodBlocks.add(Material.MANGROVE_LOG);
        woodBlocks.add(Material.MANGROVE_PLANKS);
        woodBlocks.add(Material.WARPED_STEM);
        woodBlocks.add(Material.WARPED_PLANKS);
        woodBlocks.add(Material.CRIMSON_STEM);
        woodBlocks.add(Material.CRIMSON_PLANKS);

        if (woodBlocks.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 10, 0, false, false, true));
        }

        // Weakness on granite blocks
        ArrayList<Material> graniteBlocks = new ArrayList<>();
        graniteBlocks.add(Material.GRANITE);
        graniteBlocks.add(Material.GRANITE_SLAB);
        graniteBlocks.add(Material.GRANITE_WALL);
        graniteBlocks.add(Material.GRANITE_STAIRS);
        graniteBlocks.add(Material.BLACKSTONE);
        graniteBlocks.add(Material.BLACKSTONE_SLAB);
        graniteBlocks.add(Material.BLACKSTONE_WALL);
        graniteBlocks.add(Material.BLACKSTONE_STAIRS);

        if (graniteBlocks.contains(player.getLocation().add(0, -1, 0).getBlock().getType())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 10, 0, false, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 0, false, false, true));
        }

    }
}
