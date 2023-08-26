package net.toujoustudios.hyperspecies.ability.active.light;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class AbilityFly extends Ability {

    public AbilityFly() {
        super(
                "Fly",
                List.of("§8Gain the ability to fly during §d{duration}s§8."),
                Element.LIGHT,
                AbilityType.UTILITY,
                8,
                240,
                Material.FEATHER,
                8,
                List.of("Elf", "Angel"),
                4,
                4,
                null
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(5, 7, 9, 11, 13, 15, 17, 19, 20));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.get(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.setAllowFlight(true);
        player.setFlying(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            player.setFlying(false);
            if(player.getGameMode() != GameMode.CREATIVE) player.setAllowFlight(false);
        }, 20L * duration);
        return true;
    }

}
