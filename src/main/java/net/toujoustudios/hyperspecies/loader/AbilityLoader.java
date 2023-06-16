package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.ability.active.*;
import net.toujoustudios.hyperspecies.data.ability.tree.AbilityTree;

import java.util.HashMap;

public class AbilityLoader {

    public static void initialize() {

        Ability.createAbility(new AbilityEnhancingFlame());
        Ability.createAbility(new AbilityHellblight());
        Ability.createAbility(new AbilityDemonicRage());
        Ability.createAbility(new AbilityMeteorStrike());

        HashMap<Integer, Ability> enhancingFlameAbilities = new HashMap<>();
        enhancingFlameAbilities.put(0, Ability.getAbility("Enhancing Flame"));
        enhancingFlameAbilities.put(1, Ability.getAbility("Hellblight"));
        enhancingFlameAbilities.put(2, Ability.getAbility("Demonic Rage"));
        enhancingFlameAbilities.put(10, Ability.getAbility("Meteor Strike"));
        AbilityTree.createTree("Enhancing Flame", new AbilityTree(enhancingFlameAbilities, 0));

    }

}
