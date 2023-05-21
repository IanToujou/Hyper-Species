package net.toujoustudios.hyperspecies.data.ability.active;

public class AbilityInitializer {

    public static void initialize() {

        Ability.createAbility(new AbilityEnhancingFlame());
        Ability.createAbility(new AbilityHellblight());
        Ability.createAbility(new AbilityDemonicRage());
        Ability.createAbility(new AbilityMeteorStrike());

    }

}
