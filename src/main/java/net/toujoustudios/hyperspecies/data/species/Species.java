package net.toujoustudios.hyperspecies.data.species;

import net.toujoustudios.hyperspecies.data.ability.Ability;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Species {

    private static final HashMap<String, Species> species = new HashMap<>();

    private final String name;
    private final ItemStack icon;
    private final List<Ability> abilities;
    private final List<SubSpecies> subSpecies;

    public Species(String name, ItemStack icon, List<Ability> abilities, List<SubSpecies> subSpecies) {
        this.name = name;
        this.icon = icon;
        this.abilities = abilities;
        this.subSpecies = subSpecies;
    }

    public static Species getSpecies(String name) {
        if(species.containsKey(name)) return species.get(name);
        return null;
    }

    public static void createSpecies(String name, ItemStack icon, List<Ability> abilities, List<SubSpecies> subSpecies) {
        species.put(name, new Species(name, icon, (abilities==null ? new ArrayList<>() : abilities), (subSpecies==null ? new ArrayList<>() : subSpecies)));
    }

    // GETTERS AND SETTERS

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public List<SubSpecies> getSubSpecies() {
        return subSpecies;
    }

    // STATIC METHODS

    public static HashMap<String, Species> getSpecies() {
        return species;
    }

}
