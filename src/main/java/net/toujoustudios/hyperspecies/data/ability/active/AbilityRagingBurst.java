package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityRagingBurst extends Ability {

    public AbilityRagingBurst() {
        super(
                "Raging Burst",
                List.of("§8Can only be used if the user deals", "§8damage with " + Objects.requireNonNull(Ability.getAbility("Demonic Rage")).getFullName() + "§8 to someone.", "§8Gives the user a resistance buff of§8.", "§d{duration}s§8."),
                Element.FIRE,
                AbilityType.BUFF,
                8,
                140,
                Material.SHULKER_SHELL,
                8,
                List.of("Demon", "Reptile", "Wolf"),
                6,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10,11,12,13,14,15,16,17,18));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        if(!AbilityDemonicRage.getDamagePlayers().contains(player.getUniqueId())) return false;

        AbilityDemonicRage.getDamagePlayers().remove(player.getUniqueId());
        AbilityDemonicRage.getUsingPlayers().remove(player.getUniqueId());
        PlayerManager playerManager = PlayerManager.getPlayer(player);

        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 4, 0.5f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 4, 1f);
        player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 100, 0, 2, 0);
        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 10, 0, 2, 0);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*duration, 3, false, false, true));

        return true;

    }

}
