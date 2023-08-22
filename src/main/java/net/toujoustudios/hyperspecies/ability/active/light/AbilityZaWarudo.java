package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityZaWarudo extends Ability {

    public AbilityZaWarudo() {

        super(
                "Za Warudo",
                List.of("§8Stun enemies in a range of §d{range}m", "§8for §d{duration}s§8."),
                Element.LIGHT,
                Element.FAIRY,
                AbilityType.DEBUFF,
                12,
                360,
                Material.FEATHER,
                5,
                List.of("Elf", "Angel"),
                5,
                5,
                null
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.RANGE, List.of(6, 7, 8, 9, 10, 11));
        fields.put(AbilityField.DURATION, List.of(1, 1, 2, 2, 3, 3));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.get(player);
        int range = getFieldValue(AbilityField.RANGE, playerManager.getAbilityLevel(this));
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.getWorld().spawnParticle(Particle.FLASH, player.getLocation(), 40, 0, 0, 0);
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_1, 4, 0.3f);
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = range * range;
        players.forEach(all -> {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                if (all != player) {
                    PlayerManager manager = PlayerManager.get(all);
                    manager.stun(20 * duration);
                }
            }
        });
        return true;
    }

}
