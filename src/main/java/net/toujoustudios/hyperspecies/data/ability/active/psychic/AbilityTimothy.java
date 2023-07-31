package net.toujoustudios.hyperspecies.data.ability.active.psychic;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AbilityTimothy extends Ability {

    public AbilityTimothy() {
        super(
                "Timothy",
                List.of("§8Teleports a player to their spawn."),
                Element.PSYCHIC,
                AbilityType.UTILITY,
                18,
                900,
                Material.CHORUS_FRUIT,
                5,
                List.of("Elf"),
                8,
                5,
                Objects.requireNonNull(Species.getSpecies("Elf")).getSubSpecies("Dryad")
        );

    }

    @Override
    public boolean execute(Player player) {

        Block block = player.getTargetBlock(null, 20);
        Location location = block.getLocation();

        Player target = null;
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 4 * 4;
        for (Player all : players) {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared)
                target = all;
        }
        if (target == null) return false;

        location.getWorld().spawnParticle(Particle.SMOKE_LARGE, location, 200, 0.3, 0.3, 0.3);
        location.getWorld().playSound(location, Sound.ENTITY_WITHER_AMBIENT, SoundCategory.MASTER, 5, 1f);
        if (target.getBedSpawnLocation() != null) target.teleport(target.getBedSpawnLocation());
        else target.teleport(target.getWorld().getSpawnLocation());
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 0, false, false, true));

        return true;

    }

}
