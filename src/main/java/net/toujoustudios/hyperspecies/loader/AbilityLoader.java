package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.ability.active.*;

public class AbilityLoader {

    public static void initialize() {

        Ability.createAbility(new AbilityEnhancingFlame());
        Ability.createAbility(new AbilityHellblight());
        Ability.createAbility(new AbilityDemonicRage());
        Ability.createAbility(new AbilityMeteorStrike());

    }

}
