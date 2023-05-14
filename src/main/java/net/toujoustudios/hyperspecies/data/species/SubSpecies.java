package net.toujoustudios.hyperspecies.data.species;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;

import java.util.List;

public class SubSpecies {

    private final String name;
    private final List<Ability> abilities;

    public SubSpecies(String name, List<Ability> abilities) {
        this.name = name;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

}
