package net.toujoustudios.hyperspecies.ability.active.fairy;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class AbilityLightDiffraction extends Ability {

    public AbilityLightDiffraction() {
        super(
                "Light Diffraction",
                List.of("§8Diffract the light around you, making", "§8you invisible for §d{duration}s§8."),
                Element.FAIRY,
                AbilityType.BUFF,
                4,
                120,
                Material.PRISMARINE_SHARD,
                8,
                List.of("Elf", "Angel", "Aquatilia", "Human"),
                3,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10, 11, 12, 14));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        player.getWorld().spawnParticle(Particle.FLASH, player.getLocation(), 5, 0.1, 0.1, 0.1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * duration, 0, false, false, true));

        return true;

    }

}
