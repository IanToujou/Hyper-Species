package net.toujoustudios.hyperspecies.data.ability;

import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class AbilityHellblight extends Ability {

    public AbilityHellblight() {
        super("Hellblight", ItemList.DEMON_ABILITY_HELLBLIGHT, 6, 60);
    }

    @Override
    public boolean execute(Player player) {

        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 3, 1.5f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 500, 8, 0, 8);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 8*8;
        players.forEach(all -> {
            if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                if(all != player) all.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*30, 0, false, false, true));
            }
        });

        return true;

    }

}
