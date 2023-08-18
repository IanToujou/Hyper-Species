package net.toujoustudios.hyperspecies.ability.active.electro;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class AbilityLightspeed extends Ability {

    public AbilityLightspeed() {

        super(
                "Lightspeed",
                List.of("§8Gain speed boost, jump boost and", "§8invisibility for §d{duration}s§8."),
                Element.ELECTRO,
                AbilityType.BUFF,
                5,
                120,
                Material.AMETHYST_SHARD,
                8,
                List.of("Angel", "Feline"),
                4,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10, 12, 14, 15));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        if(playerManager.getSpecies().name().equals("Feline")) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 3, false, false, true));
        else player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 1, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * duration, 1, false, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * duration, 0, false, false, true));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GUARDIAN_ATTACK, SoundCategory.MASTER, 2, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.MASTER, 3, 1f);
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 100, 0.5, 0.5, 0.5);

        return true;

    }

}
