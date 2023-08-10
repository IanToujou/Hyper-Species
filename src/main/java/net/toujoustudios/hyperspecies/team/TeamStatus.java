package net.toujoustudios.hyperspecies.team;

public enum TeamStatus {

    OPEN("Open", "§a"), INVITE("Invite", "§e"), CLOSED("Closed", "§c");

    private final String name;
    private final String color;

    TeamStatus(String name, String color) {
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
