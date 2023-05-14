package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityDemonicRage extends Ability {


    public AbilityDemonicRage() {
        super("Demonic Rage", ItemList.DEMON_ABILITY_DEMONIC_RAGE, 10, 120);
    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 4, 1f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 4, 1.5f);
        player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 100, 0, 2, 0);
        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 10, 0, 2, 0);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*30, 1, false, false, true));
        playerManager.setManaRegeneration(0.4);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            playerManager.setManaRegeneration(0.1);
        }, 20 * 30);

        return true;

    }

}
