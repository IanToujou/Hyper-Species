package net.toujoustudios.hyperspecies.log;

/**
 * A level system for the {@link Logger} class. It provides different colors and warning or error labeling.
 */
public enum LogLevel {

    DEBUG(0, "§b"), INFORMATION(1, "§a"), WARNING(2, "§e"), ERROR(3, "§c"), FATAL(4, "§4");

    private final int level;
    private final String color;

    LogLevel(int level, String color) {
        this.level = level;
        this.color = color;
    }

    /**
     * Returns the {@link Integer} level. A higher level means a more important message.
     *
     * @return The current level as a number.
     */
    public int level() {
        return level;
    }

    /**
     * Returns the color prefix that is used by the {@link org.bukkit.Bukkit} API.
     *
     * @return The color prefix for Minecraft.
     */
    public String color() {
        return color;
    }

}
