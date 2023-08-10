package net.toujoustudios.hyperspecies.ability.active.flora;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityWoodlandsBlessing extends Ability {

    public AbilityWoodlandsBlessing() {

        super(
                "Woodlands Blessing",
                List.of("§8Instantly regenerate §c❤ {heal}§8 HP for", "§8an ally."),
                Element.FLORA,
                AbilityType.HEAL,
                4,
                120,
                Material.LILAC,
                5,
                List.of("Elf", "Feline"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.HEAL, List.of(10, 12, 14, 16, 18, 20));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int heal = getFieldValue(AbilityField.HEAL, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 50);

        Player target = null;
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 5 * 5;
        for (Player all : players) {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared)
                target = all;
        }
        if (target == null) return false;

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 100, 2f);
        target.getWorld().spawnParticle(Particle.HEART, target.getLocation().add(0, 3, 0), 50, 0.3, 0.1, 0.3);

        PlayerManager targetManager = PlayerManager.getPlayer(target);
        targetManager.setHealth(targetManager.getHealth() + heal);

        return true;

    }

}
