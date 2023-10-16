package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class AbilityDarkestNight extends Ability {

    public AbilityDarkestNight() {

        super(
                "Darkest Night",
                List.of("§8"),
                Element.DARK,
                AbilityType.BUFF,
                12,
                360,
                Material.BONE,
                5,
                List.of("Wolf"),
                4,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(30, 40, 50, 60, 70, 80));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        World world = player.getWorld();
        if (world.isUltraWarm()) return false;
        long oldTime = world.getFullTime();
        int duration = getFieldValue(AbilityField.DURATION, player);
        world.setTime(18000);

        Bukkit.getOnlinePlayers().forEach(all -> {
            PlayerManager manager = PlayerManager.get(all);
            if (manager.getSpecies() != null && manager.getSpecies().name().equals("Wolf")) {
                all.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * duration, 0, false, false, true));
                all.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * duration, 0, false, false, true));
            } else
                all.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * duration, 0, false, false, true));
        });

        world.playSound(player.getLocation(), Sound.ENTITY_WOLF_HOWL, SoundCategory.MASTER, 10, 1f);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> world.setFullTime(oldTime), 20L * duration);
        return true;

    }

}
