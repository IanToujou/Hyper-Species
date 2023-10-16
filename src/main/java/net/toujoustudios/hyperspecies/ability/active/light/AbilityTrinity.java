package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AbilityTrinity extends Ability {

    public AbilityTrinity() {

        super(
                "Trinity",
                List.of("§8Fire§d 3§8 separate beams at an enemy,", "§8dealing " + Element.LIGHT.getEmoji() + " {damage}§8 each."),
                Element.LIGHT,
                AbilityType.DAMAGE,
                8,
                120,
                Material.ENDER_PEARL,
                5,
                List.of("Angel"),
                4,
                4,
                Objects.requireNonNull(Species.getSpecies("Angel")).getSubSpecies("Demi God")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 6, 7, 8, 9, 10));
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

        if (target == null) return false;

        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.MASTER, 5, 2f);

        Player finalTarget = target;
        new BukkitRunnable() {

            int i = 0;

            @Override
            public void run() {
                if (i >= 3) {
                    cancel();
                    return;
                }
                finalTarget.damage(damage, player);
                finalTarget.getWorld().strikeLightningEffect(finalTarget.getLocation());
                i++;
            }
        }.runTaskTimer(HyperSpecies.getInstance(), 20 * 3, 10);

        return true;
    }

}
