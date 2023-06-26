package net.toujoustudios.hyperspecies.data.ability.passive;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PassiveReptile extends PassiveAbility {

    @Override
    public void execute(Player player) {

        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 10, 0, false, false, true));

        List<Biome> speedBiomes = List.of(
                Biome.SWAMP,
                Biome.MANGROVE_SWAMP,
                Biome.DESERT
        );

        if(player.getLocation().add(0, -1, 0).getBlock().getType() == Material.WATER || speedBiomes.contains(player.getLocation().getBlock().getBiome())) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1, false, false, true));
        }

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
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 1, false, false, true));
        }

    }

}
