package net.toujoustudios.hyperspecies.data.ability.active;

public enum AbilityType {

    BUFF("Buff"),
    DEBUFF("Debuff"),
    TERRAIN("Terrain"),
    HEAL("Heal"),
    DAMAGE("Damage");


    private final String name;

    AbilityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
