package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.ability.active.*;
import net.toujoustudios.hyperspecies.data.ability.tree.AbilityTree;

import java.util.HashMap;
import java.util.List;

public class AbilityLoader {

    public static void initialize() {

        Ability.createAbility(new AbilityEnhancingFlame());
        Ability.createAbility(new AbilityHellblight());
        Ability.createAbility(new AbilityDemonicRage());

        Ability.createAbility(new AbilityFlameShot());
        Ability.createAbility(new AbilityFireball());
        Ability.createAbility(new AbilityMeteorStrike());
        Ability.createAbility(new AbilityRayOfDoom());
        Ability.createAbility(new AbilityBurningRain());
        Ability.createAbility(new AbilityStrikingTrail());

        HashMap<Integer, Ability> flameShotAbilities = new HashMap<>();
        flameShotAbilities.put(0, Ability.getAbility("Flame Shot"));
        flameShotAbilities.put(1, Ability.getAbility("Fireball"));
        flameShotAbilities.put(2, Ability.getAbility("Meteor Strike"));
        flameShotAbilities.put(3, Ability.getAbility("Ray Of Doom"));
        flameShotAbilities.put(10, Ability.getAbility("Burning Rain"));
        flameShotAbilities.put(11, Ability.getAbility("Striking Trail"));
        AbilityTree.createTree("Flame Shot", new AbilityTree(flameShotAbilities, List.of(1,2)));

    }

}
