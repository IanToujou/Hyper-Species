package net.toujoustudios.hyperspecies.data.element;

public enum Element {

    FIRE("Fire", "§c"),
    EARTH("Earth", "§6"),
    WATER("Water", "§9"),
    FLORA("Flora", "§2"),
    FAIRY("Fairy", "§d"),
    ELECTRO("Electro", "§e"),
    AIR("Air", "§f"),
    PSYCHIC("Psychic", "§5"),
    NORMAL("Normal", "§7"),
    LIGHT("Light", "§f"),
    DARK("Dark", "§8");

    private final String name;
    private final String color;

    Element(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

}
