package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public abstract class Ability {

    private static final HashMap<String, Ability> abilities = new HashMap<>();

    private final String name;
    private final List<String> description;
    private final Element element;
    private final int manaCost;
    private final int delay;
    private final Material material;

    public Ability(String name, List<String> description, Element element, int manaCost, int delay, Material material) {
        this.name = name;
        this.description = description;
        this.element = element;
        this.manaCost = manaCost;
        this.delay = delay;
        this.material = material;
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

    public List<String> getDescription() {
        return description;
    }

    public Element getElement() {
        return element;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getDelay() {
        return delay;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("§e" + name + " §7- " + element.getColor() + element.getName());
        List<String> lore = new ArrayList<>();
        lore.add("§b" + manaCost + " Mana §8| §6" + delay + "s");
        lore.add("§r");
        itemMeta.setLore(Stream.concat(lore.stream(), description.stream()).toList());
        item.setItemMeta(itemMeta);
        return item;
    }

    public static HashMap<String, Ability> getAbilities() {
        return abilities;
    }

}
