package net.toujoustudios.hyperspecies.ability.active.fire;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbilityDemonicRage extends Ability {

    private static final ArrayList<UUID> usingPlayers = new ArrayList<>();
    private static final ArrayList<UUID> damagePlayers = new ArrayList<>();

    public AbilityDemonicRage() {
        super(
                "Demonic Rage",
                List.of("§8The user explodes, giving them a", "§8strength buff and mana regeneration", "§8of §d{rate}/s §8for §d{duration}s§8."),
                Element.FIRE,
                AbilityType.BUFF,
                10,
                120,
                Material.POPPED_CHORUS_FRUIT,
                8,
                List.of("Demon", "Reptile", "Wolf"),
                6,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10, 12, 14, 18, 20, 24, 26, 28, 30));
        fields.put(AbilityField.RATE, List.of(1, 1, 2, 2, 3, 3, 4, 4, 5));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int xp = playerManager.getAbilityExperience(this);
        int level = playerManager.getLevelFromExperience(xp);

        int duration = getFieldValue(AbilityField.DURATION, level);
        int rate = getFieldValue(AbilityField.RATE, level) / 10;

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 4, 1f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 4, 1.5f);
        player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 100, 0, 2, 0);
        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 10, 0, 2, 0);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * duration, 1, false, false, true));
        playerManager.setManaRegeneration(rate);
        if (!usingPlayers.contains(player.getUniqueId())) usingPlayers.add(player.getUniqueId());

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            playerManager.setManaRegeneration(0.1);
            usingPlayers.remove(player.getUniqueId());
        }, 20L * duration);

        return true;

    }

    public static ArrayList<UUID> getUsingPlayers() {
        return usingPlayers;
    }

    public static ArrayList<UUID> getDamagePlayers() {
        return damagePlayers;
    }

}
