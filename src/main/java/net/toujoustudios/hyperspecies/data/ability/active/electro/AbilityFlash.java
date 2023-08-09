package net.toujoustudios.hyperspecies.data.ability.active.electro;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class AbilityFlash extends Ability {

    public AbilityFlash() {

        super(
                "Flash",
                List.of("§8Gain a small speed boost for §d{duration}s§8."),
                Element.ELECTRO,
                AbilityType.BUFF,
                3,
                60,
                Material.DIAMOND,
                5,
                List.of("Demon", "Angel", "Human", "Feline"),
                2,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        if(playerManager.getSpecies().name().equals("Feline")) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 3, false, false, true));
        else player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 1, false, false, true));

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, SoundCategory.MASTER, 2, 1.5f);
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 100, 0.5, 0.5, 0.5);

        return true;

    }

}
