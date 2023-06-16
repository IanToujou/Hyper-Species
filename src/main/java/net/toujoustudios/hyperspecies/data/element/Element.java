package net.toujoustudios.hyperspecies.data.element;

import org.bukkit.Material;

public enum Element {

    FIRE("Fire", "§c", Material.RED_STAINED_GLASS_PANE),
    EARTH("Earth", "§6", Material.BROWN_STAINED_GLASS_PANE),
    WATER("Water", "§9", Material.BLUE_STAINED_GLASS_PANE),
    FLORA("Flora", "§2", Material.LIME_STAINED_GLASS_PANE),
    FAIRY("Fairy", "§d", Material.PINK_STAINED_GLASS_PANE),
    ELECTRO("Electro", "§e", Material.YELLOW_STAINED_GLASS_PANE),
    AIR("Air", "§f", Material.LIGHT_BLUE_STAINED_GLASS_PANE),
    PSYCHIC("Psychic", "§5", Material.PURPLE_STAINED_GLASS_PANE),
    NORMAL("Normal", "§7", Material.LIGHT_GRAY_STAINED_GLASS_PANE),
    LIGHT("Light", "§f", Material.WHITE_STAINED_GLASS_PANE),
    DARK("Dark", "§8", Material.GRAY_STAINED_GLASS_PANE);

    private final String name;
    private final String color;
    private final Material material;

    Element(String name, String color, Material material) {
        this.name = name;
        this.color = color;
        this.material = material;
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

}
