package net.toujoustudios.hyperspecies.data.ability.active.fairy;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.species.Species;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityTimeDilation extends Ability {

    public AbilityTimeDilation() {

        super(
                "Time Dilation",
                List.of("§8Gain a huge speed boost during", "§d{duration}s§8."),
                Element.FAIRY,
                AbilityType.BUFF,
                8,
                360,
                Material.CLOCK,
                8,
                List.of("Elf"),
                7,
                5,
                Objects.requireNonNull(Species.getSpecies("Elf")).getSubSpecies("Guardian")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10, 11, 12, 13, 14, 15, 16, 17, 18));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 100, 0.1, 0.1, 0.1);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 2, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 2, 2f);

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 6, false, false, true));

        return true;

    }

}
