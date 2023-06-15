package net.toujoustudios.hyperspecies.data.ability.tree;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
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

    public AbilityTree(HashMap<Integer, Ability> abilities) {
        this.abilities = abilities;
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

    public Inventory buildInventory() {

        Inventory inventory = Bukkit.createInventory(null, 9*5, "Tree: " + getBaseAbility().getName());
        for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);

        return inventory;

    }

    public static HashMap<String, AbilityTree> getTrees() {
        return trees;
    }

    public static Inventory buildMainInventory() {

        Inventory inventory = Bukkit.createInventory(null, 9*6, "Ability Trees");
        for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);

        AtomicInteger i = new AtomicInteger();
        trees.forEach((name, tree) -> {
            ItemStack item = new ItemStack(Material.NETHER_STAR);
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName("§e" + name);
            itemMeta.setLore(List.of("§7View this ability tree."));
            item.setItemMeta(itemMeta);
            inventory.setItem(i.get(), item);
            i.getAndIncrement();
        });

        return inventory;

    }

}
