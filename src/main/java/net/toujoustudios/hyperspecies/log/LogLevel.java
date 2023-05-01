package net.toujoustudios.hyperspecies.log;

public enum LogLevel {

    DEBUG(0, "§b"), INFORMATION(1, "§a"), WARNING(2, "§e"), ERROR(3, "§c"), FATAL(4, "§4");

    private final int level;
    private final String color;

    LogLevel(int level, String color) {

        this.level = level;
        this.color = color;

    }

    public int getLevel() {
        return this.level;
    }

    public String getColor() {

        return this.color;

    }

}
