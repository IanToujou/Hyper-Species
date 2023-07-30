package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.active.air.*;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityEndblight;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityEngulfingDarkness;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityStrikeOfCorruption;
import net.toujoustudios.hyperspecies.data.ability.active.dark.AbilityTotalAnnihilation;
import net.toujoustudios.hyperspecies.data.ability.active.earth.*;
import net.toujoustudios.hyperspecies.data.ability.active.electro.*;
import net.toujoustudios.hyperspecies.data.ability.active.fairy.*;
import net.toujoustudios.hyperspecies.data.ability.active.fire.*;
import net.toujoustudios.hyperspecies.data.ability.active.flora.*;
import net.toujoustudios.hyperspecies.data.ability.active.psychic.AbilityPsychicWard;
import net.toujoustudios.hyperspecies.data.ability.active.psychic.AbilityTimothy;
import net.toujoustudios.hyperspecies.data.ability.active.water.*;
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
        AbilityTree.createTree("Flame Shot", new AbilityTree(flameShotAbilities, List.of(0, 1)));

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
        AbilityTree.createTree("Light Diffraction", new AbilityTree(lightDiffractionAbilities, List.of(0, 1)));

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
        Ability.createAbility(new AbilityStunSpore());

        HashMap<Integer, Ability> thornSeedlingAbilities = new HashMap<>();
        thornSeedlingAbilities.put(0, Ability.getAbility("Thorn Seedling"));
        thornSeedlingAbilities.put(1, Ability.getAbility("Raging Flora"));
        thornSeedlingAbilities.put(2, Ability.getAbility("Stun Spore"));
        AbilityTree.createTree("Thorn Seedling", new AbilityTree(thornSeedlingAbilities));

        // Psychic Ward
        Ability.createAbility(new AbilityPsychicWard());
        Ability.createAbility(new AbilityTimothy());

        HashMap<Integer, Ability> psychicWardAbilities = new HashMap<>();
        psychicWardAbilities.put(0, Ability.getAbility("Psychic Ward"));
        psychicWardAbilities.put(1, Ability.getAbility("Timothy"));
        AbilityTree.createTree("Psychic Ward", new AbilityTree(psychicWardAbilities));

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

        // Sharp Stone
        Ability.createAbility(new AbilitySharpStone());
        Ability.createAbility(new AbilityRoarOfTheStone());
        Ability.createAbility(new AbilitySpikingEarth());
        Ability.createAbility(new AbilityEarthboundThorns());

        HashMap<Integer, Ability> sharpStoneAbilities = new HashMap<>();
        sharpStoneAbilities.put(0, Ability.getAbility("Sharp Stone"));
        sharpStoneAbilities.put(1, Ability.getAbility("Roar Of The Stone"));
        sharpStoneAbilities.put(2, Ability.getAbility("Spiking Earth"));
        sharpStoneAbilities.put(3, Ability.getAbility("Earthbound Thorns"));
        AbilityTree.createTree("Sharp Stone", new AbilityTree(sharpStoneAbilities));

        // Mine Sight
        Ability.createAbility(new AbilityMineSight());
        Ability.createAbility(new AbilityWayOfTheDwarf());
        Ability.createAbility(new AbilityLuckyTooth());
        Ability.createAbility(new AbilityRevengeOfTheGround());

        HashMap<Integer, Ability> mineSightAbilities = new HashMap<>();
        mineSightAbilities.put(0, Ability.getAbility("Mine Sight"));
        mineSightAbilities.put(1, Ability.getAbility("Way Of The Dwarf"));
        mineSightAbilities.put(2, Ability.getAbility("Lucky Tooth"));
        mineSightAbilities.put(10, Ability.getAbility("Revenge Of The Ground"));
        AbilityTree.createTree("Mine Sight", new AbilityTree(mineSightAbilities, List.of(0)));

        // Stone Born
        Ability.createAbility(new AbilityStoneBorn());
        Ability.createAbility(new AbilityBornIntoStone());

        HashMap<Integer, Ability> stoneBornAbilities = new HashMap<>();
        stoneBornAbilities.put(0, Ability.getAbility("Stone Born"));
        stoneBornAbilities.put(1, Ability.getAbility("Born Into Stone"));
        AbilityTree.createTree("Stone Born", new AbilityTree(stoneBornAbilities));

        // Dash
        Ability.createAbility(new AbilityDash());
        Ability.createAbility(new AbilitySilentDash());

        HashMap<Integer, Ability> dashAbilities = new HashMap<>();
        dashAbilities.put(0, Ability.getAbility("Dash"));
        dashAbilities.put(1, Ability.getAbility("Silent Dash"));
        AbilityTree.createTree("Dash", new AbilityTree(dashAbilities));

        // Aquatic Surge
        Ability.createAbility(new AbilityAquaticSurge());
        Ability.createAbility(new AbilityTidalWave());
        Ability.createAbility(new AbilityTyphoon());
        Ability.createAbility(new AbilityHydroblast());

        HashMap<Integer, Ability> aquaticSurgeAbilities = new HashMap<>();
        aquaticSurgeAbilities.put(0, Ability.getAbility("Aquatic Surge"));
        aquaticSurgeAbilities.put(1, Ability.getAbility("Tidal Wave"));
        aquaticSurgeAbilities.put(2, Ability.getAbility("Typhoon"));
        aquaticSurgeAbilities.put(10, Ability.getAbility("Hydroblast"));
        AbilityTree.createTree("Aquatic Surge", new AbilityTree(aquaticSurgeAbilities, List.of(0)));

        // Healing Waters
        Ability.createAbility(new AbilityHealingWaters());
        Ability.createAbility(new AbilityHydrokinesis());
        Ability.createAbility(new AbilityAquaShield());
        Ability.createAbility(new AbilityWaterDome());
        Ability.createAbility(new AbilityMistyVeil());
        Ability.createAbility(new AbilityWhirlpoolWhispers());
        Ability.createAbility(new AbilityDrowningGrasp());

        HashMap<Integer, Ability> healingWatersAbilities = new HashMap<>();
        healingWatersAbilities.put(0, Ability.getAbility("Healing Waters"));
        healingWatersAbilities.put(1, Ability.getAbility("Hydrokinesis"));
        healingWatersAbilities.put(2, Ability.getAbility("Aqua Shield"));
        healingWatersAbilities.put(3, Ability.getAbility("Water Dome"));
        healingWatersAbilities.put(10, Ability.getAbility("Misty Veil"));
        healingWatersAbilities.put(11, Ability.getAbility("Whirlpool Whispers"));
        healingWatersAbilities.put(12, Ability.getAbility("Drowning Grasp"));
        AbilityTree.createTree("Healing Waters", new AbilityTree(healingWatersAbilities, List.of(0)));

        // Torrential Rain
        Ability.createAbility(new AbilityTorrentialRain());
        Ability.createAbility(new AbilityHailstormBarrage());
        Ability.createAbility(new AbilityFrostbite());

        HashMap<Integer, Ability> torrentialRainAbilities = new HashMap<>();
        torrentialRainAbilities.put(0, Ability.getAbility("Torrential Rain"));
        torrentialRainAbilities.put(1, Ability.getAbility("Hailstorm Barrage"));
        torrentialRainAbilities.put(10, Ability.getAbility("Frostbite"));
        AbilityTree.createTree("Torrential Rain", new AbilityTree(torrentialRainAbilities, List.of(0)));

        // Raigeki
        Ability.createAbility(new AbilityRaigeki());

        HashMap<Integer, Ability> raigekiAbilities = new HashMap<>();
        raigekiAbilities.put(0, Ability.getAbility("Raigeki"));
        AbilityTree.createTree("Raigeki", new AbilityTree(raigekiAbilities));

        // Whirlwind
        Ability.createAbility(new AbilityWhirlwind());
        Ability.createAbility(new AbilityWindShock());
        Ability.createAbility(new AbilitySlashingWind());

        HashMap<Integer, Ability> whirlwindAbilities = new HashMap<>();
        whirlwindAbilities.put(0, Ability.getAbility("Whirlwind"));
        whirlwindAbilities.put(1, Ability.getAbility("Wind Shock"));
        whirlwindAbilities.put(10, Ability.getAbility("Slashing Wind"));
        AbilityTree.createTree("Whirlwind", new AbilityTree(whirlwindAbilities, List.of(0)));

    }

}
