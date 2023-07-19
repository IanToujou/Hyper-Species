package net.toujoustudios.hyperspecies.data.ability.active.fairy;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityProtectTheHomeland extends Ability {

    public AbilityProtectTheHomeland() {

        super(
                "Protect The Homeland",
                List.of("§8Instantly give a shield of §e⛨ {shield}", "§8and a short time of resistance for", "§8allies within §d{range}m§8."),
                Element.FAIRY,
                AbilityType.BUFF,
                10,
                240,
                Material.SWEET_BERRIES,
                8,
                List.of("Elf"),
                5,
                4
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

        fields.put(AbilityField.SHIELD, List.of(10,12,14,16,18,20,22,24,25));
        fields.put(AbilityField.RANGE, List.of(6,6,7,7,8,8,9,9,10));

        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int shield = getFieldValue(AbilityField.SHIELD, playerManager.getAbilityLevel(this));
        Location location = player.getLocation();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3, 1f);
        player.getWorld().spawnParticle(Particle.CRIT_MAGIC, player.getLocation(), 100, 3, 0, 3);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 8*8;
        players.forEach(all -> {
            if(all.getLocation().distanceSquared(location) <= radiusSquared) {
                all.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*3, 5, false, false, true));
                PlayerManager manager = PlayerManager.getPlayer(all);
                manager.setShield(manager.getShield() + shield);
            }
        });

        return true;

    }

}
