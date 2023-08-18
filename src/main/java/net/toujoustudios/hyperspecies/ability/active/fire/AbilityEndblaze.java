package net.toujoustudios.hyperspecies.ability.active.fire;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbilityEndblaze extends Ability {

    private static final ArrayList<UUID> players = new ArrayList<>();

    public AbilityEndblaze() {

        super(
                "Endblaze",
                List.of("§8Enter a state for a duration of §d{duration}s", "§8where you inflict blindness if you", "§8hit an enemy."),
                Element.FIRE,
                Element.DARK,
                AbilityType.BUFF,
                7,
                90,
                Material.DRIED_KELP,
                8,
                List.of("Demon", "Human"),
                6,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10, 11, 12, 14, 15, 16, 17, 18, 20));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        Location location = player.getLocation();

        player.getWorld().playSound(location, Sound.ITEM_FIRECHARGE_USE, 3, 2f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, location, 300, 0.3, 0, 0.3);

        if (!players.contains(player.getUniqueId())) players.add(player.getUniqueId());

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> players.remove(player.getUniqueId()), 20L * duration);

        return true;

    }

    public static ArrayList<UUID> getPlayers() {
        return players;
    }

}
