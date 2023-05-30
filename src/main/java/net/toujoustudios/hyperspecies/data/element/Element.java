package net.toujoustudios.hyperspecies.data.element;

public enum Element {

    FIRE("Fire", "§c"),
    LAVA("Lava", "§4"),
    ASH("Ash", "§c"),
    EARTH("Earth", "§6"),
    STONE("Stone", "§7"),
    METAL("Metal", "§7"),
    WATER("Water", "§9"),
    FOG("Fog", "§3"),
    ICE("Ice", "§b"),
    FLORA("Flora", "§2"),
    FAIRY("Fairy", "§d"),
    POISON("Poison", "§a"),
    ELECTRO("Electro", "§e"),
    THUNDER("Thunder", "§6"),
    MAGNETIC("Magnetic", "§e"),
    AIR("Air", "§f"),
    SKY("Sky", "§b"),
    STORM("Storm", "§3"),
    PSYCHIC("Psychic", "§5"),
    LOVE("Love", "§d"),
    NIGHTMARE("Nightmare", "§5"),
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
