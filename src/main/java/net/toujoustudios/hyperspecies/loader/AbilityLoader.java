package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.ability.active.*;
import net.toujoustudios.hyperspecies.data.ability.active.air.AbilityICBM;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityEndblight;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityEngulfingDarkness;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityStrikeOfCorruption;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityTotalAnnihilation;
import net.toujoustudios.hyperspecies.data.ability.active.electro.AbilityFlash;
import net.toujoustudios.hyperspecies.data.ability.active.electro.AbilityLightspeed;
import net.toujoustudios.hyperspecies.data.ability.active.air.AbilitySupersonicSpeed;
import net.toujoustudios.hyperspecies.data.ability.active.fairy.*;
import net.toujoustudios.hyperspecies.data.ability.active.fire.*;
import net.toujoustudios.hyperspecies.data.ability.active.flora.*;
import net.toujoustudios.hyperspecies.data.ability.tree.AbilityTree;

import java.util.HashMap;
import java.util.List;

public class AbilityLoader {

    public static void initialize() {

        // Hellblight
        Ability.createAbility(new AbilityHellblight());
        Ability.createAbility(new AbilityFlamingBlight());
        Ability.createAbility(new AbilityEndblight());
        Ability.createAbility(new AbilityEngulfingDarkness());

        HashMap<Integer, Ability> hellblightAbilities = new HashMap<>();
        hellblightAbilities.put(0, Ability.getAbility("Hellblight"));
        hellblightAbilities.put(1, Ability.getAbility("Flaming Blight"));
        AbilityTree.createTree("Hellblight", new AbilityTree(hellblightAbilities));

        HashMap<Integer, Ability> endblightAbilities = new HashMap<>();
        endblightAbilities.put(0, Ability.getAbility("Endblight"));
        endblightAbilities.put(1, Ability.getAbility("Engulfing Darkness"));
        AbilityTree.createTree("Endblight", new AbilityTree(endblightAbilities));

        // Flame Shot
        Ability.createAbility(new AbilityFlameShot());
        Ability.createAbility(new AbilityFireball());
        Ability.createAbility(new AbilityMeteorStrike());
        Ability.createAbility(new AbilityRayOfDoom());
        Ability.createAbility(new AbilityTotalAnnihilation());
        Ability.createAbility(new AbilityStrikeOfCorruption());
        Ability.createAbility(new AbilityBurningRain());
        Ability.createAbility(new AbilityStrikingTrail());
        // TODO: Dark Matter
        // TODO: Black Hole
        // TODO: Collapsing Universe

        HashMap<Integer, Ability> flameShotAbilities = new HashMap<>();
        flameShotAbilities.put(0, Ability.getAbility("Flame Shot"));
        flameShotAbilities.put(1, Ability.getAbility("Fireball"));
        flameShotAbilities.put(2, Ability.getAbility("Meteor Strike"));
        flameShotAbilities.put(3, Ability.getAbility("Ray Of Doom"));
        flameShotAbilities.put(10, Ability.getAbility("Burning Rain"));
        flameShotAbilities.put(11, Ability.getAbility("Striking Trail"));
        AbilityTree.createTree("Flame Shot", new AbilityTree(flameShotAbilities, List.of(0,1)));

        HashMap<Integer, Ability> totalAnnihilationAbilities = new HashMap<>();
        totalAnnihilationAbilities.put(0, Ability.getAbility("Total Annihilation"));
        totalAnnihilationAbilities.put(1, Ability.getAbility("Strike Of Corruption"));
        AbilityTree.createTree("Total Annihilation", new AbilityTree(totalAnnihilationAbilities));

        // Enhancing Flame
        Ability.createAbility(new AbilityEnhancingFlame());
        Ability.createAbility(new AbilityDemonicRage());
        Ability.createAbility(new AbilityRagingBurst());
        Ability.createAbility(new AbilityMagmaticDetonation());
        Ability.createAbility(new AbilityHellblaze());
        Ability.createAbility(new AbilityEndblaze());

        HashMap<Integer, Ability> enhancingFlameAbilities = new HashMap<>();
        enhancingFlameAbilities.put(0, Ability.getAbility("Enhancing Flame"));
        enhancingFlameAbilities.put(1, Ability.getAbility("Demonic Rage"));
        enhancingFlameAbilities.put(2, Ability.getAbility("Raging Burst"));
        enhancingFlameAbilities.put(3, Ability.getAbility("Magmatic Detonation"));
        enhancingFlameAbilities.put(10, Ability.getAbility("Hellblaze"));
        enhancingFlameAbilities.put(11, Ability.getAbility("Endblaze"));
        AbilityTree.createTree("Enhancing Flame", new AbilityTree(enhancingFlameAbilities, List.of(0)));

        // Light Diffraction
        Ability.createAbility(new AbilityLightDiffraction());
        Ability.createAbility(new AbilityManaPool());
        Ability.createAbility(new AbilityProtectTheHomeland());
        Ability.createAbility(new AbilityTimeDilation());
        Ability.createAbility(new AbilityStarwhisper());
        Ability.createAbility(new AbilityCelestialShot());

        HashMap<Integer, Ability> lightDiffractionAbilities = new HashMap<>();
        lightDiffractionAbilities.put(0, Ability.getAbility("Light Diffraction"));
        lightDiffractionAbilities.put(1, Ability.getAbility("Mana Pool"));
        lightDiffractionAbilities.put(2, Ability.getAbility("Protect The Homeland"));
        lightDiffractionAbilities.put(3, Ability.getAbility("Time Dilation"));
        lightDiffractionAbilities.put(10, Ability.getAbility("Starwhisper"));
        lightDiffractionAbilities.put(11, Ability.getAbility("Celestial Shot"));
        AbilityTree.createTree("Light Diffraction", new AbilityTree(lightDiffractionAbilities, List.of(0)));

        // Healing Nature
        Ability.createAbility(new AbilityHealingNature());
        Ability.createAbility(new AbilityWoodlandsBlessing());

        HashMap<Integer, Ability> healingNatureAbilities = new HashMap<>();
        healingNatureAbilities.put(0, Ability.getAbility("Healing Nature"));
        healingNatureAbilities.put(1, Ability.getAbility("Woodlands Blessing"));
        AbilityTree.createTree("Healing Nature", new AbilityTree(healingNatureAbilities));

        // Thorn Seedling
        Ability.createAbility(new AbilityThornSeedling());
        Ability.createAbility(new AbilityRagingFlora());
        Ability.createAbility(new AbilityGreenHell());

        HashMap<Integer, Ability> thornSeedlingAbilities = new HashMap<>();
        thornSeedlingAbilities.put(0, Ability.getAbility("Thorn Seedling"));
        thornSeedlingAbilities.put(1, Ability.getAbility("Raging Flora"));
        thornSeedlingAbilities.put(2, Ability.getAbility("Green Hell"));
        AbilityTree.createTree("Thorn Seedling", new AbilityTree(thornSeedlingAbilities));

        // Quick Growth
        Ability.createAbility(new AbilityQuickGrowth());
        Ability.createAbility(new AbilityFertilizingRain());

        HashMap<Integer, Ability> quickGrowthAbilities = new HashMap<>();
        quickGrowthAbilities.put(0, Ability.getAbility("Quick Growth"));
        quickGrowthAbilities.put(1, Ability.getAbility("Fertilizing Rain"));
        AbilityTree.createTree("Quick Growth", new AbilityTree(quickGrowthAbilities));

        // Flash
        Ability.createAbility(new AbilityFlash());
        Ability.createAbility(new AbilityLightspeed());

        HashMap<Integer, Ability> flashAbilities = new HashMap<>();
        flashAbilities.put(0, Ability.getAbility("Flash"));
        flashAbilities.put(1, Ability.getAbility("Lightspeed"));
        AbilityTree.createTree("Flash", new AbilityTree(flashAbilities));

        // Supersonic Speed
        Ability.createAbility(new AbilitySupersonicSpeed());
        Ability.createAbility(new AbilityICBM());

        HashMap<Integer, Ability> supersonicSpeedAbilities = new HashMap<>();
        supersonicSpeedAbilities.put(0, Ability.getAbility("Supersonic Speed"));
        supersonicSpeedAbilities.put(1, Ability.getAbility("ICBM"));
        AbilityTree.createTree("Supersonic Speed", new AbilityTree(supersonicSpeedAbilities));

    }

}
