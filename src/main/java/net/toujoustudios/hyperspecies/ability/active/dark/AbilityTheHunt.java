package net.toujoustudios.hyperspecies.ability.active.dark;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class AbilityTheHunt extends Ability {

    private static final ArrayList<UUID> players = new ArrayList<>();

    public AbilityTheHunt() {

        super(
                "The Hunt",
                List.of("§8Send an enemy player into a state in", "§8which they are being hunted. It lasts", "§d{duration}s§8 and during the hunt, the enemy", "§8will glow and damaging them yields", "§8experience."),
                Element.DARK,
                AbilityType.BUFF,
                8,
                180,
                Material.ARROW,
                5,
                List.of("Feline", "Wolf"),
                3,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DURATION, List.of(60, 70, 80, 90, 100, 110));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        int duration = getFieldValue(AbilityField.DURATION, player);
        PlayerManager playerManager = PlayerManager.get(player);
        Block block = player.getTargetBlock(null, 50);

        Player target = null;
        double radiusSquared = 5 * 5;
        for (Player all : HyperSpecies.getInstance().getServer().getOnlinePlayers()) {
            if (all != player && all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared) target = all;
        }
        if (target == null) return false;
        if(players.contains(target.getUniqueId())) return false;
        PlayerManager manager = PlayerManager.get(target);
        if(manager.getTeam() != null && playerManager.getTeam() != null && manager.getTeam() == playerManager.getTeam()) return false;

        players.add(target.getUniqueId());
        target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * duration, 0, false, false, true));
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, SoundCategory.MASTER, 5, 0.5f);

        Player finalTarget = target;
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> players.remove(finalTarget.getUniqueId()), 20L * duration);

        return true;

    }

    public static ArrayList<UUID> getPlayers() {
        return players;
    }

}
