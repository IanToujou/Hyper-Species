package net.toujoustudios.hyperspecies.data.ability.active;

import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class Ability {

    private static final HashMap<String, Ability> abilities = new HashMap<>();

    private final String name;
    private final int manaCost;
    private final int delay;

    public Ability(String name, int manaCost, int delay) {
        this.name = name;
        this.manaCost = manaCost;
        this.delay = delay;
    }

    public static void createAbility(Ability ability) {
        abilities.put(ability.getName(), ability);
    }

    public static Ability getAbility(String name) {
        if(abilities.containsKey(name)) return abilities.get(name);
        return null;
    }

    /**
     * Executes the ability, without draining mana or applying any delay. The mana and delay should be managed externally.
     *
     * @param player The player that is executing the spell.
     * @return A boolean whether the spell has been executed successfully or not.
     */
    public abstract boolean execute(Player player);

    public String getName() {
        return name;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDelay() {
        return delay;
    }

    public static HashMap<String, Ability> getAbilities() {
        return abilities;
    }

}
