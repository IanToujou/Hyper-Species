package net.toujoustudios.hyperspecies.data.ability.active;

import net.toujoustudios.hyperspecies.data.element.Element;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public abstract class Ability {

    private static final HashMap<String, Ability> abilities = new HashMap<>();

    private final String name;
    private final List<String> description;
    private final Element element;
    private final Element secondaryElement;
    private final AbilityType type;
    private final int manaCost;
    private final int delay;
    private final Material material;
    private int maxLevel;

    private HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

    public Ability(String name, List<String> description, Element element, AbilityType type, int manaCost, int delay, Material material, int maxLevel) {
        this.name = name;
        this.description = description;
        this.element = element;
        this.type = type;
        this.manaCost = manaCost;
        this.delay = delay;
        this.material = material;
        this.secondaryElement = null;
    }

    public Ability(String name, List<String> description, Element element, Element secondaryElement, AbilityType type, int manaCost, int delay, Material material, int maxLevel) {
        this.name = name;
        this.description = description;
        this.element = element;
        this.secondaryElement = secondaryElement;
        this.type = type;
        this.manaCost = manaCost;
        this.delay = delay;
        this.material = material;
        this.maxLevel = maxLevel;
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

    public Element getSecondaryElement() {
        return secondaryElement;
    }

    public AbilityType getType() {
        return type;
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

    public int getMaxLevel() {
        return maxLevel;
    }

    public ItemStack getItem() {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        if(secondaryElement != null) itemMeta.setDisplayName(element.getColor() + element.getEmoji() + secondaryElement.getColor() + secondaryElement.getEmoji() + element.getColor() + " " + name);
        else itemMeta.setDisplayName(element.getColor() + element.getEmoji() + " " + name + " §7(§aLVL {level}§7)");
        List<String> lore = new ArrayList<>();
        lore.add("§8" + type.getName());
        lore.add("§b" + manaCost + " Mana §8/ §6" + delay + "s");
        lore.add("§r");
        List<String> list = new ArrayList<>(Stream.concat(lore.stream(), description.stream()).toList());
        list.add("§r");
        list.add("§a§lUNLOCKED");
        itemMeta.setLore(list);
        item.setItemMeta(itemMeta);
        return item;

    }

    public HashMap<AbilityField, List<Integer>> getFields() {
        return fields;
    }

    public void setFields(HashMap<AbilityField, List<Integer>> fields) {
        this.fields = fields;
    }

    public void addField(AbilityField field, ArrayList<Integer> list) {
        fields.put(field, list);
    }

    public List<Integer> getField(AbilityField field) {
        return fields.getOrDefault(field, Collections.emptyList());
    }

    public int getFieldValue(AbilityField field, int level) {
        if(fields.get(field) == null || fields.get(field).size() == 0) return 0;
        if(fields.get(field).size() < level) return fields.get(field).get(fields.get(field).size()-1);
        return fields.get(field).get(level);
    }

    public static HashMap<String, Ability> getAbilities() {
        return abilities;
    }

}
