package net.toujoustudios.hyperspecies.data.ability;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Ability {

    private final String name;
    private final ItemStack icon;
    private final int manaCost;
    private final int delay;

    public Ability(String name, ItemStack icon, int manaCost, int delay) {
        this.name = name;
        this.icon = icon;
        this.manaCost = manaCost;
        this.delay = delay;
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

    public ItemStack getIcon() {
        return icon;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDelay() {
        return delay;
    }

}
