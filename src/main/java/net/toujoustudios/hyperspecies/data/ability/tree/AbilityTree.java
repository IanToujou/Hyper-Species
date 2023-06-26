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
import org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;

public class AbilityTree {

    private static final HashMap<String, AbilityTree> trees = new HashMap<>();

    private final HashMap<Integer, Ability> abilities;
    private List<Integer> links = new ArrayList<>();

    public AbilityTree(HashMap<Integer, Ability> abilities) {
        this.abilities = abilities;
        links.add(-1);
    }

    public AbilityTree(HashMap<Integer, Ability> abilities, int link) {
        this.abilities = abilities;
        links.add(link);
    }

    public AbilityTree(HashMap<Integer, Ability> abilities, List<Integer> links) {
        this.abilities = abilities;
        this.links = links;
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

    public List<Integer> getLinks() {
        return links;
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

            if(ability.isAvailableForSpecies(playerManager.getSpecies())) {

                int xp = playerManager.getAbilityExperience(ability);
                int level = playerManager.getLevelFromExperience(xp);
                double filledBars = ((double) playerManager.getExperienceSinceLastLevel(xp) / playerManager.getRelativeLevelThreshold(level)) * 50;
                double emptyBars = 50 - filledBars;

                ItemStack item = ability.getItem();
                ItemMeta itemMeta = item.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{level}", String.valueOf(level)));
                List<String> oldLore = itemMeta.getLore();
                ArrayList<String> newLore = new ArrayList<>();

                StringBuilder barBuilder = new StringBuilder();
                barBuilder.append("§7[");

                for(int i = 0; i < Math.floor(filledBars); i++) {
                    barBuilder.append("§a|");
                }

                for(int i = 0; i < Math.ceil(emptyBars); i++) {
                    barBuilder.append("§8|");
                }

                barBuilder.append("§7]");

                assert oldLore != null;
                oldLore.forEach(line -> {
                    line = line.replace("{damage}", String.valueOf(ability.getFieldValue(AbilityField.DAMAGE, level)));
                    line = line.replace("{duration}", String.valueOf(ability.getFieldValue(AbilityField.DURATION, level)));
                    line = line.replace("{rate}", String.valueOf(ability.getFieldValue(AbilityField.RATE, level)));
                    line = line.replace("{range}", String.valueOf(ability.getFieldValue(AbilityField.RANGE, level)));
                    line = line.replace("{lockStatus}", playerManager.hasAbility(ability) ? "§a§lUNLOCKED" : "§c§lLOCKED");
                    if(line.contains("{xpBar}")) {
                        line = line.replace("{xpBar}", barBuilder.toString());
                        if(playerManager.hasAbility(ability)) newLore.add(line);
                    } else newLore.add(line);
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

                if(links.contains(0) && slot == 10) inventory.setItem(19, ItemList.TREE_TRACK_LOCKED);
                if(links.contains(1) && slot == 11) inventory.setItem(21, ItemList.TREE_TRACK_LOCKED);
                if(links.contains(2) && slot == 12) inventory.setItem(23, ItemList.TREE_TRACK_LOCKED);
                if(links.contains(3) && slot == 13) inventory.setItem(25, ItemList.TREE_TRACK_LOCKED);

                // Set abilities
                if(slot == 0) inventory.setItem(10, item);
                if(slot == 1) inventory.setItem(12, item);
                if(slot == 2) inventory.setItem(14, item);
                if(slot == 3) inventory.setItem(16, item);
                if(slot == 10) inventory.setItem(28, item);
                if(slot == 11) inventory.setItem(30, item);
                if(slot == 12) inventory.setItem(32, item);
                if(slot == 13) inventory.setItem(34, item);

            }

        });

        return inventory;

    }

    public static HashMap<String, AbilityTree> getTrees() {
        return trees;
    }

    public static Inventory buildMainInventory(Player player, int page) {

        if(page == 0) {

            Inventory inventory = Bukkit.createInventory(null, 9*6, "Ability Trees: Page 1");
            for(int i = inventory.getSize()-9; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);
            inventory.setItem(49, ItemList.CANCEL);
            inventory.setItem(53, ItemList.NEXT);
            inventory.setItem(0, Element.FIRE.getItem(false));
            inventory.setItem(9, Element.EARTH.getItem(false));
            inventory.setItem(18, Element.WATER.getItem(false));
            inventory.setItem(27, Element.FLORA.getItem(false));
            inventory.setItem(36, Element.FAIRY.getItem(false));

            int indexFire = 0;
            int indexEarth = 0;
            int indexWater = 0;
            int indexFlora = 0;
            int indexFairy = 0;

            for(Map.Entry<String, AbilityTree> entry : trees.entrySet()) {

                AbilityTree tree = trees.get(entry.getKey());
                Element element = tree.getBaseAbility().getElement();

                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta itemMeta = item.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(tree.getBaseAbility().getFullName());
                itemMeta.setLore(List.of("§7View this ability tree."));
                item.setItemMeta(itemMeta);

                if(element == Element.FIRE) {
                    inventory.setItem(indexFire+1, item);
                    indexFire++;
                } else if(element == Element.EARTH) {
                    inventory.setItem(9+indexEarth+1, item);
                    indexEarth++;
                } else if(element == Element.WATER) {
                    inventory.setItem(18+indexWater+1, item);
                    indexWater++;
                } else if(element == Element.FLORA) {
                    inventory.setItem(27+indexFlora+1, item);
                    indexFlora++;
                } else if(element == Element.FAIRY) {
                    inventory.setItem(36+indexFairy+1, item);
                    indexFairy++;
                }

            }

            return inventory;

        } else if(page == 1) {

            Inventory inventory = Bukkit.createInventory(null, 9*6, "Ability Trees: Page 2");
            for(int i = inventory.getSize()-9; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);
            inventory.setItem(45, ItemList.PREVIOUS);
            inventory.setItem(49, ItemList.CANCEL);
            inventory.setItem(53, ItemList.NEXT);
            inventory.setItem(0, Element.ELECTRO.getItem(false));
            inventory.setItem(9, Element.AIR.getItem(false));
            inventory.setItem(18, Element.PSYCHIC.getItem(false));
            inventory.setItem(27, Element.NORMAL.getItem(false));
            inventory.setItem(36, Element.LIGHT.getItem(false));

            int indexElectro = 0;
            int indexAir = 0;
            int indexPsychic = 0;
            int indexNormal = 0;
            int indexLight = 0;

            for(Map.Entry<String, AbilityTree> entry : trees.entrySet()) {

                AbilityTree tree = trees.get(entry.getKey());
                Element element = tree.getBaseAbility().getElement();

                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta itemMeta = item.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(tree.getBaseAbility().getFullName());
                itemMeta.setLore(List.of("§7View this ability tree."));
                item.setItemMeta(itemMeta);

                if(element == Element.ELECTRO) {
                    inventory.setItem(indexElectro+1, item);
                    indexElectro++;
                } else if(element == Element.AIR) {
                    inventory.setItem(9+indexAir+1, item);
                    indexAir++;
                } else if(element == Element.PSYCHIC) {
                    inventory.setItem(18+indexPsychic+1, item);
                    indexPsychic++;
                } else if(element == Element.NORMAL) {
                    inventory.setItem(27+indexNormal+1, item);
                    indexNormal++;
                } else if(element == Element.LIGHT) {
                    inventory.setItem(36+indexLight+1, item);
                    indexLight++;
                }

            }

            return inventory;

        } else {

            Inventory inventory = Bukkit.createInventory(null, 9*2, "Ability Trees: Page 3");
            for(int i = inventory.getSize()-9; i < inventory.getSize(); i++) inventory.setItem(i, ItemList.FILLER);
            inventory.setItem(9, ItemList.PREVIOUS);
            inventory.setItem(13, ItemList.CANCEL);
            inventory.setItem(0, Element.DARK.getItem(false));

            int indexDark = 0;

            for(Map.Entry<String, AbilityTree> entry : trees.entrySet()) {

                AbilityTree tree = trees.get(entry.getKey());
                Element element = tree.getBaseAbility().getElement();

                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta itemMeta = item.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(tree.getBaseAbility().getFullName());
                itemMeta.setLore(List.of("§7View this ability tree."));
                item.setItemMeta(itemMeta);

                if(element == Element.DARK) {
                    inventory.setItem(indexDark+1, item);
                    indexDark++;
                }

            }

            return inventory;

        }

    }

}