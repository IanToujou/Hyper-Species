package net.toujoustudios.hyperspecies.data.species;

import net.toujoustudios.hyperspecies.data.ability.passive.PassiveAbility;
import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Species {

    private static final HashMap<String, Species> species = new HashMap<>();

    private final String name;
    private final String prefix;
    private final ItemStack icon;
    private final List<SubSpecies> subSpecies;
    private final PassiveAbility passive;
    private final List<Element> weaknesses;

    public Species(String name, String prefix, ItemStack icon, List<SubSpecies> subSpecies, PassiveAbility passive, List<Element> weaknesses) {
        this.name = name;
        this.prefix = prefix;
        this.icon = icon;
        this.subSpecies = subSpecies;
        this.passive = passive;
        this.weaknesses = weaknesses;
    }

    public static Species getSpecies(String name) {
        if(species.containsKey(name)) return species.get(name);
        return null;
    }

    public static void createSpecies(String name, String prefix, ItemStack icon, List<SubSpecies> subSpecies, PassiveAbility passive, List<Element> weaknesses) {
        species.put(name, new Species(
                name,
                prefix,
                icon,
                (subSpecies==null ? new ArrayList<>() : subSpecies),
                passive,
                (weaknesses==null ? new ArrayList<>() : weaknesses)
        ));
    }

    public SubSpecies getSubSpecies(String name) {
        for(SubSpecies all : subSpecies) {
            if(all.getName().equalsIgnoreCase(name)) return all;
        }
        return null;
    }

    // GETTERS AND SETTERS

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public List<SubSpecies> getSubSpecies() {
        return subSpecies;
    }

    public PassiveAbility getPassive() {
        return passive;
    }

    public List<Element> getWeaknesses() {
        return weaknesses;
    }

    // STATIC METHODS

    public static HashMap<String, Species> getSpecies() {
        return species;
    }

}
