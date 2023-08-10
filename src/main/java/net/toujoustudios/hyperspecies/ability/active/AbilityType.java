package net.toujoustudios.hyperspecies.ability.active;

public enum AbilityType {

    BUFF("Buff"),
    DEBUFF("Debuff"),
    TERRAIN("Terrain"),
    HEAL("Heal"),
    DAMAGE("Damage"),
    UTILITY("Utility");


    private final String name;

    AbilityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
