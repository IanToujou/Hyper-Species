package net.toujoustudios.hyperspecies.data.element;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public enum Element {

    FIRE("Fire", "§c", Material.RED_STAINED_GLASS_PANE, "\uD83D\uDD25"),
    EARTH("Earth", "§6", Material.BROWN_STAINED_GLASS_PANE, "Ⓞ"),
    WATER("Water", "§9", Material.BLUE_STAINED_GLASS_PANE, "\uD83C\uDF0A"),
    FLORA("Flora", "§2", Material.LIME_STAINED_GLASS_PANE, "✿"),
    FAIRY("Fairy", "§d", Material.PINK_STAINED_GLASS_PANE, "❃"),
    ELECTRO("Electro", "§e", Material.YELLOW_STAINED_GLASS_PANE, "⚡"),
    AIR("Air", "§f", Material.LIGHT_BLUE_STAINED_GLASS_PANE, "☄"),
    PSYCHIC("Psychic", "§5", Material.PURPLE_STAINED_GLASS_PANE, "☣"),
    NORMAL("Normal", "§7", Material.LIGHT_GRAY_STAINED_GLASS_PANE, "☯"),
    LIGHT("Light", "§f", Material.WHITE_STAINED_GLASS_PANE, "۞"),
    DARK("Dark", "§8", Material.GRAY_STAINED_GLASS_PANE, "₪");

    private final String name;
    private final String color;
    private final Material material;
    private final String emoji;

    Element(String name, String color, Material material, String emoji) {
        this.name = name;
        this.color = color;
        this.material = material;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Material getMaterial() {
        return material;
    }

    public String getEmoji() {
        return emoji;
    }

    public ItemStack getItem(boolean description) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(color + emoji + " " + name);
        if(description) itemMeta.setLore(List.of(""));
        item.setItemMeta(itemMeta);

        return item;

    }

    public String getFullName() {
        return color + emoji + " " + name;
    }

}
