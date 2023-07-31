package net.toujoustudios.hyperspecies.data.ability.active.air;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.species.Species;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilitySupersonicSpeed extends Ability {

    public AbilitySupersonicSpeed() {

        super(
                "Supersonic Speed",
                List.of("§8Gain a huge speed boost and go", "§8supersonic during §d{duration}s§8."),
                Element.AIR,
                AbilityType.UTILITY,
                8,
                60,
                Material.COBWEB,
                5,
                List.of("Feline"),
                6,
                4,
                Objects.requireNonNull(Species.getSpecies("Feline")).getSubSpecies("Leopard")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 70);

        Location location = player.getLocation();
        Vector direction = location.getDirection();
        direction.normalize();
        direction.multiply(15);
        location.add(direction);

        if (location.getBlock().getType() != Material.AIR || block.getType() != Material.AIR) return false;
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PISTON_EXTEND, SoundCategory.MASTER, 2, 1.5f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 3, 0.8f);
        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 100, 0.5, 0.5, 0.5);
        player.teleport(new Location(location.getWorld(), location.getX(), player.getLocation().getY() + 0.5, location.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
        player.setVelocity(player.getLocation().getDirection());
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 2, false, false, true));

        return true;

    }

}
