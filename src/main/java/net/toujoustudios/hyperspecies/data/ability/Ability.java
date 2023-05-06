package net.toujoustudios.hyperspecies.data.ability;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

    public abstract void execute(Player player);

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
