package net.toujoustudios.hyperspecies.data.ability.tree;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AbilityTree {

    private static final HashMap<String, AbilityTree> trees = new HashMap<>();

    private final HashMap<Integer, Ability> abilities;
    private final int link;

    public AbilityTree(HashMap<Integer, Ability> abilities) {
        this.abilities = abilities;
        this.link = -1;
    }

    public AbilityTree(HashMap<Integer, Ability> abilities, int link) {
        this.abilities = abilities;
        this.link = link;
    }

    public static void createTree(String name, AbilityTree tree) {
        trees.put(name, tree);
    }

    public static AbilityTree getTree(String name) {
        if(trees.containsKey(name)) return trees.get(name);
        return null;
    }

    public Ability getBaseAbility() {
        return abilities.getOrDefault(0, null);
    }

    public HashMap<Integer, Ability> getAbilities() {
        return abilities;
    }

    public int getLink() {
        return link;
    }

    public Inventory buildInventory() {

        Inventory inventory = Bukkit.createInventory(null, 9*6, "Tree: " + getBaseAbility().getName());
        ItemStack element = new ItemStack(getBaseAbility().getElement().getMaterial());
        ItemMeta elementMeta = element.getItemMeta();
        assert elementMeta != null;
        elementMeta.setDisplayName(" ");
        element.setItemMeta(elementMeta);
        for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);
        for(int i = 45; i < inventory.getSize(); i++) inventory.setItem(i, element);
        inventory.setItem(49, ItemList.CANCEL);

        abilities.forEach((slot, ability) -> {

            // Fill first line
            if(slot == 1) inventory.setItem(11, ItemList.TREE_TRACK_LOCKED);
            if(slot == 1) inventory.setItem(12, ItemList.TREE_TRACK_LOCKED);
            if(slot == 2) inventory.setItem(13, ItemList.TREE_TRACK_LOCKED);
            if(slot == 2) inventory.setItem(14, ItemList.TREE_TRACK_LOCKED);
            if(slot == 3) inventory.setItem(15, ItemList.TREE_TRACK_LOCKED);

            // Fill second line
            if(slot == 11) inventory.setItem(29, ItemList.TREE_TRACK_LOCKED);
            if(slot == 11) inventory.setItem(30, ItemList.TREE_TRACK_LOCKED);
            if(slot == 12) inventory.setItem(31, ItemList.TREE_TRACK_LOCKED);
            if(slot == 12) inventory.setItem(32, ItemList.TREE_TRACK_LOCKED);
            if(slot == 13) inventory.setItem(33, ItemList.TREE_TRACK_LOCKED);

            if(link == 0) inventory.setItem(19, ItemList.TREE_TRACK_LOCKED);
            if(link == 1) inventory.setItem(21, ItemList.TREE_TRACK_LOCKED);
            if(link == 2) inventory.setItem(23, ItemList.TREE_TRACK_LOCKED);
            if(link == 3) inventory.setItem(25, ItemList.TREE_TRACK_LOCKED);

            // Set abilities
            if(slot == 0) inventory.setItem(10, ability.getItem());
            if(slot == 1) inventory.setItem(12, ability.getItem());
            if(slot == 2) inventory.setItem(14, ability.getItem());
            if(slot == 3) inventory.setItem(16, ability.getItem());
            if(slot == 10) inventory.setItem(28, ability.getItem());
            if(slot == 11) inventory.setItem(30, ability.getItem());
            if(slot == 12) inventory.setItem(32, ability.getItem());
            if(slot == 13) inventory.setItem(34, ability.getItem());

        });

        return inventory;

    }

    public static HashMap<String, AbilityTree> getTrees() {
        return trees;
    }

    public static Inventory buildMainInventory() {

        Inventory inventory = Bukkit.createInventory(null, 9*6, "Ability Trees");
        for(int i = inventory.getSize()-9; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);
        inventory.setItem(45, ItemList.PREVIOUS);
        inventory.setItem(49, ItemList.CANCEL);
        inventory.setItem(53, ItemList.NEXT);
        inventory.setItem(0, ItemList.ELEMENT_FIRE);
        inventory.setItem(9, ItemList.ELEMENT_EARTH);
        inventory.setItem(18, ItemList.ELEMENT_WATER);
        inventory.setItem(27, ItemList.ELEMENT_FLORA);
        inventory.setItem(36, ItemList.ELEMENT_FAIRY);

        AtomicInteger i = new AtomicInteger();
        trees.forEach((name, tree) -> {
            ItemStack item = new ItemStack(Material.NETHER_STAR);
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName("§e" + name);
            itemMeta.setLore(List.of("§7View this ability tree.", "§r", "§7Element: " + tree.getBaseAbility().getElement().getColor() + tree.getBaseAbility().getElement().getName()));
            item.setItemMeta(itemMeta);
            int slot = i.get();
            if(slot==0 || slot==9 || slot==18 || slot==27 || slot==36 || slot==45) slot++;
            inventory.setItem(slot, item);
            i.getAndIncrement();
        });

        return inventory;

    }

}
