package net.toujoustudios.hyperspecies.ability.active.electro;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.ability.active.AbilityType;
import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AbilityRaigeki extends Ability {

    public AbilityRaigeki() {

        super(
                "Raigeki",
                List.of("§8Summons a lightning bolt that strikes", "§8down any foe, dealing " + Element.ELECTRO.getEmoji() + " {damage}§8."),
                Element.ELECTRO,
                AbilityType.DAMAGE,
                5,
                120,
                Material.AMETHYST_SHARD,
                5,
                List.of("Reptile", "Angel", "Human", "Aquatilia"),
                4,
                3
        );

        HashMap<AbilityField, List<Integer>> fields = new HashMap<>();
        fields.put(AbilityField.DAMAGE, List.of(5, 6, 7, 8, 9, 10));
        setFields(fields);

    }

    @Override
    public boolean execute(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        int damage = getFieldValue(AbilityField.DAMAGE, playerManager.getAbilityLevel(this));
        Block block = player.getTargetBlock(null, 50);
        Location location = block.getLocation();
        location.getWorld().strikeLightningEffect(location);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 5, 1f);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 2 * 2;
        for (Player all : players) {
            if (all.getWorld() == player.getWorld() && all.getLocation().distanceSquared(location) <= radiusSquared) {
                all.damage(damage, player);
            }
        }

        return true;

    }

}
