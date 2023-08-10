package net.toujoustudios.hyperspecies.species;

import net.toujoustudios.hyperspecies.ability.passive.PassiveAbility;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public record Species(String name, String prefix, ItemStack icon, List<SubSpecies> subSpecies, PassiveAbility passive) {

    private static final HashMap<String, Species> species = new HashMap<>();

    public static Species getSpecies(String name) {
        if (species.containsKey(name)) return species.get(name);
        return null;
    }

    public static void createSpecies(String name, String prefix, ItemStack icon, List<SubSpecies> subSpecies, PassiveAbility passive) {
        species.put(name, new Species(
                name,
                prefix,
                icon,
                (subSpecies == null ? new ArrayList<>() : subSpecies),
                passive
        ));
    }

    public SubSpecies getSubSpecies(String name) {
        for (SubSpecies all : subSpecies) {
            if (all.name().equalsIgnoreCase(name)) return all;
        }
        return null;
    }

    public static HashMap<String, Species> getSpecies() {
        return species;
    }

}
