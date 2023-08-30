package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityWrathOfTheSun extends Ability {

    public AbilityWrathOfTheSun() {

        super(
                "Wrath Of The Sun",
                List.of("§8Charge an orbital strike originating", "§8from the sun, dealing " + Element.LIGHT.getEmoji() + " {damage}§8."),
                Element.LIGHT,
                AbilityType.DAMAGE,
                14,
                240,
                Material.ENDER_EYE,
                8,
                List.of("Angel"),
                5,
                5,
                Objects.requireNonNull(Species.getSpecies("Angel")).getSubSpecies("Demi God")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(30, 35, 40, 45, 50, 55, 60, 65, 70));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.get(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 30);

        Player target = null;
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 5 * 5;
        for (Player all : players) {
            if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared)
                target = all;
        }

        if(target == null) return false;

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.MASTER, 5, 1f);

        Player finalTarget = target;

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            finalTarget.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, finalTarget.getLocation().add(0,10,0), 50, 1, 20, 1);
            finalTarget.getWorld().playSound(finalTarget.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.MASTER, 3, 1f);
            finalTarget.getWorld().playSound(finalTarget.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 3, 1f);
            finalTarget.damage(damage, player);
        }, 20 * 3);

        return true;

    }

}
