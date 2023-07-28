package net.toujoustudios.hyperspecies.data.ability.active.earth;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbilityBornIntoStone extends Ability {

    private static final List<UUID> players = new ArrayList<>();

    public AbilityBornIntoStone() {

        super(
                "Born Into Stone",
                List.of("§8Summon a protection layer of stone", "§8that makes you invulnerable, but", "§8also unable to move for §d{duration}s§8."),
                Element.EARTH,
                AbilityType.BUFF,
                8,
                210,
                Material.RAW_COPPER,
                5,
                List.of("Reptile", "Dwarf"),
                2,
                1
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(2, 3, 4, 5, 6, 7));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, SoundCategory.MASTER, 2, 1f);
        playerManager.stun(duration * 20);
        players.add(player.getUniqueId());
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> players.remove(player.getUniqueId()), 20L * duration);
        return true;
    }

    public static List<UUID> getPlayers() {
        return players;
    }

}
