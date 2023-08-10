package net.toujoustudios.hyperspecies.ability.active.water;

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

public class AbilityHealingWaters extends Ability {

    public AbilityHealingWaters() {

        super(
                "Healing Waters",
                List.of("§8Channels pure water energy to heal", "§8a player by §c❤ {heal}§8."),
                Element.WATER,
                AbilityType.HEAL,
                4,
                50,
                Material.CLAY_BALL,
                5,
                List.of("Reptile", "Human", "Aquatilia"),
                1,
                2
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.HEAL, List.of(8, 9, 10, 12, 14, 15));
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

        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 2f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 2, 2f);
        target.getWorld().spawnParticle(Particle.WATER_SPLASH, target.getLocation().add(0, 2, 0), 50, 0.3, 0.1, 0.3);

        PlayerManager targetManager = PlayerManager.getPlayer(target);
        targetManager.setHealth(targetManager.getHealth() + heal);

        return true;

    }

}
