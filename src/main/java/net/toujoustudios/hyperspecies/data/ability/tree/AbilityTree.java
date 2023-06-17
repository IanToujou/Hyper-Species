package net.toujoustudios.hyperspecies.data.ability.tree;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.AbilityField;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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

    public Inventory buildInventory(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

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

            int level = playerManager.getLevelFromExperience(playerManager.getAbilityExperience(ability));

            ItemStack item = ability.getItem();
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{level}", String.valueOf(level)));
            List<String> oldLore = itemMeta.getLore();
            ArrayList<String> newLore = new ArrayList<>();

            assert oldLore != null;
            oldLore.forEach(line -> {
                line = line.replace("{duration}", String.valueOf(ability.getFieldValue(AbilityField.DURATION, level)));
                newLore.add(line);
            });

            itemMeta.setLore(newLore);
            item.setItemMeta(itemMeta);

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
            if(slot == 0) inventory.setItem(10, item);
            if(slot == 1) inventory.setItem(12, item);
            if(slot == 2) inventory.setItem(14, item);
            if(slot == 3) inventory.setItem(16, item);
            if(slot == 10) inventory.setItem(28, item);
            if(slot == 11) inventory.setItem(30, item);
            if(slot == 12) inventory.setItem(32, item);
            if(slot == 13) inventory.setItem(34, item);

        });

        return inventory;

    }

    public static HashMap<String, AbilityTree> getTrees() {
        return trees;
    }

    public static Inventory buildMainInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9*6, "Ability Trees");
        for(int i = inventory.getSize()-9; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);
        inventory.setItem(45, ItemList.PREVIOUS);
        inventory.setItem(49, ItemList.CANCEL);
        inventory.setItem(53, ItemList.NEXT);
        inventory.setItem(0, Element.FIRE.getItem(false));
        inventory.setItem(9, Element.EARTH.getItem(false));
        inventory.setItem(18, Element.WATER.getItem(false));
        inventory.setItem(27, Element.FLORA.getItem(false));
        inventory.setItem(36, Element.FAIRY.getItem(false));

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
