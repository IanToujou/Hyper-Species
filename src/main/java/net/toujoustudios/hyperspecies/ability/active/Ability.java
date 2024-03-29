package net.toujoustudios.hyperspecies.ability.active;

import net.toujoustudios.hyperspecies.element.Element;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.Species;
import net.toujoustudios.hyperspecies.species.SubSpecies;
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
    private final Element secondaryElement;
    private final AbilityType type;
    private final int manaCost;
    private final int delay;
    private final Material material;
    private final int maxLevel;
    private final List<String> species;
    private final int cost;
    private final int weight;
    private final SubSpecies subSpecies;

    private HashMap<AbilityField, List<Integer>> fields = new HashMap<>();

    public Ability(String name, List<String> description, Element element, AbilityType type, int manaCost, int delay, Material material, int maxLevel, List<String> species, int cost, int weight) {
        this.name = name;
        this.description = description;
        this.element = element;
        this.secondaryElement = null;
        this.type = type;
        this.manaCost = manaCost;
        this.delay = delay;
        this.material = material;
        this.maxLevel = maxLevel;
        this.species = species;
        this.cost = cost;
        this.weight = weight;
        this.subSpecies = null;
    }

    public Ability(String name, List<String> description, Element element, Element secondaryElement, AbilityType type, int manaCost, int delay, Material material, int maxLevel, List<String> species, int cost, int weight) {
        this.name = name;
        this.description = description;
        this.element = element;
        this.secondaryElement = secondaryElement;
        this.type = type;
        this.manaCost = manaCost;
        this.delay = delay;
        this.material = material;
        this.maxLevel = maxLevel;
        this.species = species;
        this.cost = cost;
        this.weight = weight;
        this.subSpecies = null;
    }

    public Ability(String name, List<String> description, Element element, AbilityType type, int manaCost, int delay, Material material, int maxLevel, List<String> species, int cost, int weight, SubSpecies subSpecies) {
        this.name = name;
        this.description = description;
        this.element = element;
        this.secondaryElement = null;
        this.type = type;
        this.manaCost = manaCost;
        this.delay = delay;
        this.material = material;
        this.maxLevel = maxLevel;
        this.species = species;
        this.cost = cost;
        this.weight = weight;
        this.subSpecies = subSpecies;
    }

    public Ability(String name, List<String> description, Element element, Element secondaryElement, AbilityType type, int manaCost, int delay, Material material, int maxLevel, List<String> species, int cost, int weight, SubSpecies subSpecies) {
        this.name = name;
        this.description = description;
        this.element = element;
        this.secondaryElement = secondaryElement;
        this.type = type;
        this.manaCost = manaCost;
        this.delay = delay;
        this.material = material;
        this.maxLevel = maxLevel;
        this.species = species;
        this.cost = cost;
        this.weight = weight;
        this.subSpecies = subSpecies;
    }

    public static void create(Ability ability) {
        abilities.put(ability.name(), ability);
    }

    /**
     * Returns the ability by using its name, if present. If the ability does not exist, it will return null.
     *
     * @param name The name of the ability.
     * @return The ability if it is present.
     */
    public static Ability get(String name) {
        if (abilities.containsKey(name)) return abilities.get(name);
        return null;
    }

    /**
     * Executes the ability, without draining mana or applying any delay. The mana and delay should be managed externally.
     *
     * @param player The player that is executing the spell.
     * @return A boolean whether the spell has been executed successfully or not.
     */
    public abstract boolean execute(Player player);

    public String name() {
        return name;
    }

    public String fullName() {
        if (secondaryElement != null)
            return element.getEmoji() + " " + secondaryElement.getEmoji() + element.getColor() + " " + name;
        else return element.getEmoji() + " " + name;
    }

    public List<String> description() {
        return description;
    }

    public Element element() {
        return element;
    }

    public Element getSecondaryElement() {
        return secondaryElement;
    }

    public AbilityType getType() {
        return type;
    }

    public int manaCost() {
        return manaCost;
    }

    public int delay() {
        return delay;
    }

    public Material material() {
        return material;
    }

    public int maxLevel() {
        return maxLevel;
    }

    public ItemStack item() {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(fullName() + " §7(§aLVL {level}§7)");
        List<String> lore = new ArrayList<>();
        lore.add("§8" + type.getName() + " Spell");
        lore.add("§b" + manaCost + " Mana §8/ §6" + delay + "s §8/ §5" + weight + " Weight");
        lore.add("§r");
        List<String> list = new ArrayList<>(Stream.concat(lore.stream(), description.stream()).toList());
        list.add("§r");
        list.add("{xpBar} {xpLeft}");
        list.add("{lockStatus}");
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

    public void addField(AbilityField field, List<Integer> list) {
        fields.put(field, list);
    }

    public int getFieldValue(AbilityField field, int level) {
        if (fields.get(field) == null || fields.get(field).isEmpty()) return 0;
        if (fields.get(field).size() < level) return fields.get(field).get(fields.get(field).size() - 1);
        return fields.get(field).get(level);
    }

    public int getFieldValue(AbilityField field, Player player) {
        return getFieldValue(field, PlayerManager.get(player).getAbilityLevel(this));
    }

    public List<String> getSpecies() {
        return species;
    }

    public boolean available(Species species) {
        return this.species.contains(species.name());
    }

    public boolean available(SubSpecies subSpecies) {
        if (subSpecies() != null) {
            return subSpecies != null && subSpecies.equals(this.subSpecies);
        } else return true;
    }

    public int cost() {
        return cost;
    }

    public int weight() {
        return weight;
    }

    public SubSpecies subSpecies() {
        return subSpecies;
    }

    public static HashMap<String, Ability> get() {
        return abilities;
    }

}
