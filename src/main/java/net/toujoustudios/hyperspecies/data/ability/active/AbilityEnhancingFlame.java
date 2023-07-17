package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AbilityEnhancingFlame extends Ability {

    private static final ArrayList<UUID> players = new ArrayList<>();

    public AbilityEnhancingFlame() {

        super(
                "Enhancing Flame",
                List.of("§8Enhances the users weapons, allowing", "§8them to deal " + Element.FIRE.getFullName() + " §8damage and ", "§8setting enemies on fire for §d{duration}s§8."),
                Element.FIRE,
                AbilityType.BUFF,
                6,
                90,
                Material.BLAZE_POWDER,
                8,
                List.of("Demon", "Reptile", "Human", "Feline", "Dwarf", "Wolf"),
                List.of("")
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(10,15,20,25,30,35,40,50,60));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int xp = playerManager.getAbilityExperience(this);
        int level = playerManager.getLevelFromExperience(xp);

        int duration = getFieldValue(AbilityField.DURATION, level);

        Location location = player.getLocation();
        Block block = location.getBlock();

        player.getWorld().playSound(location, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 3, 0.8f);
        player.getWorld().spawnParticle(Particle.FLAME, location, 300, 0.3, 0, 0.3);
        player.getWorld().spawnParticle(Particle.LAVA, location, 50, 0, 2, 0);

        if(block.getType() == Material.AIR) {
            player.getWorld().getBlockAt(player.getLocation()).setType(Material.FIRE);
        }

        players.add(player.getUniqueId());

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> players.remove(player.getUniqueId()), 20L *duration);

        return true;

    }

    public static ArrayList<UUID> getPlayers() {
        return players;
    }

}
