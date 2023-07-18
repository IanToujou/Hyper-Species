package net.toujoustudios.hyperspecies.data.ability.active.fire;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbilityHellblaze extends Ability {

    private static final ArrayList<UUID> players = new ArrayList<>();

    public AbilityHellblaze() {

        super(
                "Hellblaze",
                List.of("§8Enter a state that lasts §d{duration}§8 where", "§8you ignite the enemy on hit."),
                Element.FIRE,
                AbilityType.BUFF,
                6,
                90,
                Material.HONEYCOMB,
                8,
                List.of("Demon", "Human", "Dwarf"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10,12,14,16,18,20,22,25,30));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));

        Location location = player.getLocation();

        player.getWorld().playSound(location, Sound.ITEM_FIRECHARGE_USE, 3, 1f);
        player.getWorld().spawnParticle(Particle.FLAME, location, 300, 0.3, 0, 0.3);

        if(!players.contains(player.getUniqueId())) players.add(player.getUniqueId());

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> players.remove(player.getUniqueId()), 20L * duration);

        return true;

    }

    public static ArrayList<UUID> getPlayers() {
        return players;
    }

}
