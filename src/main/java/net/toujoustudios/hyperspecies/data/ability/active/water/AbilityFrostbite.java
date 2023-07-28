package net.toujoustudios.hyperspecies.data.ability.active.water;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityFrostbite extends Ability {

    public AbilityFrostbite() {

        super(
                "Frostbite",
                List.of("§8Freezes the target with icy water", "§8causing " + Element.WATER.getEmoji() + " {damage}§8 damage and slowing", "§8them for §d{duration}s§8."),
                Element.WATER,
                AbilityType.DAMAGE,
                6,
                180,
                Material.PRISMARINE_SHARD,
                5,
                List.of("Reptile", "Human", "Aquatilia"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5,7,9,11,13,15));
        fields.put(AbilityField.DURATION, List.of(5,6,7,8,9,10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        int duration = getFieldValue(AbilityField.DURATION, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 50);

        Player target = null;
        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 5 * 5;
        for(Player all : players) {
            if(all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(block.getLocation()) <= radiusSquared) target = all;
        }
        if(target == null) return false;

        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.MASTER, 2, 0f);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 2, 0f);
        target.getWorld().spawnParticle(Particle.WATER_SPLASH, target.getLocation().add(0, 2, 0), 50, 0.3, 0.1, 0.3);
        target.damage(damage, player);
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * duration, 2, false, false, true));

        return true;

    }

}
