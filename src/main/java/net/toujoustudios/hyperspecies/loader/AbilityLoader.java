package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.air.*;
import net.toujoustudios.hyperspecies.ability.active.dark.*;
import net.toujoustudios.hyperspecies.ability.active.earth.*;
import net.toujoustudios.hyperspecies.ability.active.electro.*;
import net.toujoustudios.hyperspecies.ability.active.fairy.*;
import net.toujoustudios.hyperspecies.ability.active.fire.*;
import net.toujoustudios.hyperspecies.ability.active.flora.*;
import net.toujoustudios.hyperspecies.ability.active.light.*;
import net.toujoustudios.hyperspecies.ability.active.psychic.AbilityPsychicWard;
import net.toujoustudios.hyperspecies.ability.active.psychic.AbilityTimothy;
import net.toujoustudios.hyperspecies.ability.active.water.*;
import net.toujoustudios.hyperspecies.ability.tree.AbilityTree;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;

import java.util.HashMap;
import java.util.List;

public class AbilityLoader {

    public static void initialize() {

        // Hellblight
        Ability.create(new AbilityHellblight());
        Ability.create(new AbilityFlamingBlight());
        Ability.create(new AbilityEndblight());
        Ability.create(new AbilityEngulfingDarkness());

        HashMap<Integer, Ability> hellblightAbilities = new HashMap<>();
        hellblightAbilities.put(0, Ability.get("Hellblight"));
        hellblightAbilities.put(1, Ability.get("Flaming Blight"));
        AbilityTree.create("Hellblight", new AbilityTree(hellblightAbilities));

        HashMap<Integer, Ability> endblightAbilities = new HashMap<>();
        endblightAbilities.put(0, Ability.get("Endblight"));
        endblightAbilities.put(1, Ability.get("Engulfing Darkness"));
        AbilityTree.create("Endblight", new AbilityTree(endblightAbilities));

        // Flame Shot
        Ability.create(new AbilityFlameShot());
        Ability.create(new AbilityFireball());
        Ability.create(new AbilityMeteorStrike());
        Ability.create(new AbilityRayOfDoom());
        Ability.create(new AbilityTotalAnnihilation());
        Ability.create(new AbilityStrikeOfCorruption());
        Ability.create(new AbilityBurningRain());
        Ability.create(new AbilityStrikingTrail());

        HashMap<Integer, Ability> flameShotAbilities = new HashMap<>();
        flameShotAbilities.put(0, Ability.get("Flame Shot"));
        flameShotAbilities.put(1, Ability.get("Fireball"));
        flameShotAbilities.put(2, Ability.get("Meteor Strike"));
        flameShotAbilities.put(3, Ability.get("Ray Of Doom"));
        flameShotAbilities.put(10, Ability.get("Burning Rain"));
        flameShotAbilities.put(11, Ability.get("Striking Trail"));
        AbilityTree.create("Flame Shot", new AbilityTree(flameShotAbilities, List.of(0, 1)));

        HashMap<Integer, Ability> totalAnnihilationAbilities = new HashMap<>();
        totalAnnihilationAbilities.put(0, Ability.get("Total Annihilation"));
        totalAnnihilationAbilities.put(1, Ability.get("Strike Of Corruption"));
        AbilityTree.create("Total Annihilation", new AbilityTree(totalAnnihilationAbilities));

        // Dark Matter
        Ability.create(new AbilityDarkMatter());
        Ability.create(new AbilityCollapsingUniverse());

        HashMap<Integer, Ability> darkMatterAbilities = new HashMap<>();
        darkMatterAbilities.put(0, Ability.get("Dark Matter"));
        darkMatterAbilities.put(1, Ability.get("Collapsing Universe"));
        AbilityTree.create("Dark Matter", new AbilityTree(darkMatterAbilities));

        // Enhancing Flame
        Ability.create(new AbilityEnhancingFlame());
        Ability.create(new AbilityDemonicRage());
        Ability.create(new AbilityRagingBurst());
        Ability.create(new AbilityMagmaticDetonation());
        Ability.create(new AbilityHellblaze());
        Ability.create(new AbilityEndblaze());

        HashMap<Integer, Ability> enhancingFlameAbilities = new HashMap<>();
        enhancingFlameAbilities.put(0, Ability.get("Enhancing Flame"));
        enhancingFlameAbilities.put(1, Ability.get("Demonic Rage"));
        enhancingFlameAbilities.put(2, Ability.get("Raging Burst"));
        enhancingFlameAbilities.put(3, Ability.get("Magmatic Detonation"));
        enhancingFlameAbilities.put(10, Ability.get("Hellblaze"));
        enhancingFlameAbilities.put(11, Ability.get("Endblaze"));
        AbilityTree.create("Enhancing Flame", new AbilityTree(enhancingFlameAbilities, List.of(0)));

        // Light Diffraction
        Ability.create(new AbilityLightDiffraction());
        Ability.create(new AbilityManaPool());
        Ability.create(new AbilityProtectTheHomeland());
        Ability.create(new AbilityTimeDilation());
        Ability.create(new AbilityStarwhisper());
        Ability.create(new AbilityCelestialShot());

        HashMap<Integer, Ability> lightDiffractionAbilities = new HashMap<>();
        lightDiffractionAbilities.put(0, Ability.get("Light Diffraction"));
        lightDiffractionAbilities.put(1, Ability.get("Mana Pool"));
        lightDiffractionAbilities.put(2, Ability.get("Protect The Homeland"));
        lightDiffractionAbilities.put(3, Ability.get("Time Dilation"));
        lightDiffractionAbilities.put(10, Ability.get("Starwhisper"));
        lightDiffractionAbilities.put(11, Ability.get("Celestial Shot"));
        AbilityTree.create("Light Diffraction", new AbilityTree(lightDiffractionAbilities, List.of(0, 1)));

        // Levitation
        Ability.create(new AbilityLevitation());
        Ability.create(new AbilityFly());
        Ability.create(new AbilityBlindingLight());
        Ability.create(new AbilityZaWarudo());
        Ability.create(new AbilityCleanse());
        Ability.create(new AbilityPurify());

        HashMap<Integer, Ability> levitationAbilities = new HashMap<>();
        levitationAbilities.put(0, Ability.get("Levitation"));
        levitationAbilities.put(1, Ability.get("Fly"));
        levitationAbilities.put(2, Ability.get("Blinding Light"));
        levitationAbilities.put(3, Ability.get("Za Warudo"));
        levitationAbilities.put(10, Ability.get("Cleanse"));
        levitationAbilities.put(11, Ability.get("Purify"));
        AbilityTree.create("Levitation", new AbilityTree(levitationAbilities, List.of(0,1)));

        // Focus
        Ability.create(new AbilityFocus());
        Ability.create(new AbilityCosmicSight());
        Ability.create(new AbilityTrinity());
        Ability.create(new AbilityWrathOfTheSun());

        HashMap<Integer, Ability> focusAbilities = new HashMap<>();
        focusAbilities.put(0, Ability.get("Focus"));
        focusAbilities.put(1, Ability.get("Cosmic Sight"));
        focusAbilities.put(2, Ability.get("Trinity"));
        focusAbilities.put(3, Ability.get("Wrath Of The Sun"));
        AbilityTree.create("Focus", new AbilityTree(focusAbilities));

        // Healing Nature
        Ability.create(new AbilityHealingNature());
        Ability.create(new AbilityWoodlandsBlessing());

        HashMap<Integer, Ability> healingNatureAbilities = new HashMap<>();
        healingNatureAbilities.put(0, Ability.get("Healing Nature"));
        healingNatureAbilities.put(1, Ability.get("Woodlands Blessing"));
        AbilityTree.create("Healing Nature", new AbilityTree(healingNatureAbilities));

        // Thorn Seedling
        Ability.create(new AbilityThornSeedling());
        Ability.create(new AbilityRagingFlora());
        Ability.create(new AbilityStunSpore());

        HashMap<Integer, Ability> thornSeedlingAbilities = new HashMap<>();
        thornSeedlingAbilities.put(0, Ability.get("Thorn Seedling"));
        thornSeedlingAbilities.put(1, Ability.get("Raging Flora"));
        thornSeedlingAbilities.put(2, Ability.get("Stun Spore"));
        AbilityTree.create("Thorn Seedling", new AbilityTree(thornSeedlingAbilities));

        // Psychic Ward
        Ability.create(new AbilityPsychicWard());
        Ability.create(new AbilityTimothy());

        HashMap<Integer, Ability> psychicWardAbilities = new HashMap<>();
        psychicWardAbilities.put(0, Ability.get("Psychic Ward"));
        psychicWardAbilities.put(1, Ability.get("Timothy"));
        AbilityTree.create("Psychic Ward", new AbilityTree(psychicWardAbilities));

        // Quick Growth
        Ability.create(new AbilityQuickGrowth());
        Ability.create(new AbilityFertilizingRain());

        HashMap<Integer, Ability> quickGrowthAbilities = new HashMap<>();
        quickGrowthAbilities.put(0, Ability.get("Quick Growth"));
        quickGrowthAbilities.put(1, Ability.get("Fertilizing Rain"));
        AbilityTree.create("Quick Growth", new AbilityTree(quickGrowthAbilities));

        // The Hunt
        Ability.create(new AbilityTheHunt());
        Ability.create(new AbilityDarkestNight());
        Ability.create(new AbilityJetBlackSimulation());

        HashMap<Integer, Ability> theHuntAbilities = new HashMap<>();
        theHuntAbilities.put(0, Ability.get("The Hunt"));
        theHuntAbilities.put(1, Ability.get("Darkest Night"));
        theHuntAbilities.put(2, Ability.get("Jet Black Simulation"));
        AbilityTree.create("The Hunt", new AbilityTree(theHuntAbilities));

        // Bite
        Ability.create(new AbilityBite());

        HashMap<Integer, Ability> biteAbilities = new HashMap<>();
        biteAbilities.put(0, Ability.get("Bite"));
        AbilityTree.create("Bite", new AbilityTree(biteAbilities));

        // Flash
        Ability.create(new AbilityFlash());
        Ability.create(new AbilityLightspeed());

        HashMap<Integer, Ability> flashAbilities = new HashMap<>();
        flashAbilities.put(0, Ability.get("Flash"));
        flashAbilities.put(1, Ability.get("Lightspeed"));
        AbilityTree.create("Flash", new AbilityTree(flashAbilities));

        // Supersonic Speed
        Ability.create(new AbilitySupersonicSpeed());
        Ability.create(new AbilityICBM());

        HashMap<Integer, Ability> supersonicSpeedAbilities = new HashMap<>();
        supersonicSpeedAbilities.put(0, Ability.get("Supersonic Speed"));
        supersonicSpeedAbilities.put(1, Ability.get("ICBM"));
        AbilityTree.create("Supersonic Speed", new AbilityTree(supersonicSpeedAbilities));

        // Sharp Stone
        Ability.create(new AbilitySharpStone());
        Ability.create(new AbilityRoarOfTheStone());
        Ability.create(new AbilitySpikingEarth());
        Ability.create(new AbilityEarthboundThorns());

        HashMap<Integer, Ability> sharpStoneAbilities = new HashMap<>();
        sharpStoneAbilities.put(0, Ability.get("Sharp Stone"));
        sharpStoneAbilities.put(1, Ability.get("Roar Of The Stone"));
        sharpStoneAbilities.put(2, Ability.get("Spiking Earth"));
        sharpStoneAbilities.put(3, Ability.get("Earthbound Thorns"));
        AbilityTree.create("Sharp Stone", new AbilityTree(sharpStoneAbilities));

        // Mine Sight
        Ability.create(new AbilityMineSight());
        Ability.create(new AbilityWayOfTheDwarf());
        Ability.create(new AbilityLuckyTooth());
        Ability.create(new AbilityRevengeOfTheGround());

        HashMap<Integer, Ability> mineSightAbilities = new HashMap<>();
        mineSightAbilities.put(0, Ability.get("Mine Sight"));
        mineSightAbilities.put(1, Ability.get("Way Of The Dwarf"));
        mineSightAbilities.put(2, Ability.get("Lucky Tooth"));
        mineSightAbilities.put(10, Ability.get("Revenge Of The Ground"));
        AbilityTree.create("Mine Sight", new AbilityTree(mineSightAbilities, List.of(0)));

        // Stone Born
        Ability.create(new AbilityStoneBorn());
        Ability.create(new AbilityBornIntoStone());

        HashMap<Integer, Ability> stoneBornAbilities = new HashMap<>();
        stoneBornAbilities.put(0, Ability.get("Stone Born"));
        stoneBornAbilities.put(1, Ability.get("Born Into Stone"));
        AbilityTree.create("Stone Born", new AbilityTree(stoneBornAbilities));

        // Dash
        Ability.create(new AbilityDash());
        Ability.create(new AbilitySilentDash());

        HashMap<Integer, Ability> dashAbilities = new HashMap<>();
        dashAbilities.put(0, Ability.get("Dash"));
        dashAbilities.put(1, Ability.get("Silent Dash"));
        AbilityTree.create("Dash", new AbilityTree(dashAbilities));

        // Aquatic Surge
        Ability.create(new AbilityAquaticSurge());
        Ability.create(new AbilityTidalWave());
        Ability.create(new AbilityTyphoon());
        Ability.create(new AbilityHydroblast());

        HashMap<Integer, Ability> aquaticSurgeAbilities = new HashMap<>();
        aquaticSurgeAbilities.put(0, Ability.get("Aquatic Surge"));
        aquaticSurgeAbilities.put(1, Ability.get("Tidal Wave"));
        aquaticSurgeAbilities.put(2, Ability.get("Typhoon"));
        aquaticSurgeAbilities.put(10, Ability.get("Hydroblast"));
        AbilityTree.create("Aquatic Surge", new AbilityTree(aquaticSurgeAbilities, List.of(0)));

        // Healing Waters
        Ability.create(new AbilityHealingWaters());
        Ability.create(new AbilityHydrokinesis());
        Ability.create(new AbilityAquaShield());
        Ability.create(new AbilityWaterDome());
        Ability.create(new AbilityMistyVeil());
        Ability.create(new AbilityWhirlpoolWhispers());
        Ability.create(new AbilityDrowningGrasp());

        HashMap<Integer, Ability> healingWatersAbilities = new HashMap<>();
        healingWatersAbilities.put(0, Ability.get("Healing Waters"));
        healingWatersAbilities.put(1, Ability.get("Hydrokinesis"));
        healingWatersAbilities.put(2, Ability.get("Aqua Shield"));
        healingWatersAbilities.put(3, Ability.get("Water Dome"));
        healingWatersAbilities.put(10, Ability.get("Misty Veil"));
        healingWatersAbilities.put(11, Ability.get("Whirlpool Whispers"));
        healingWatersAbilities.put(12, Ability.get("Drowning Grasp"));
        AbilityTree.create("Healing Waters", new AbilityTree(healingWatersAbilities, List.of(0)));

        // Torrential Rain
        Ability.create(new AbilityTorrentialRain());
        Ability.create(new AbilityHailstormBarrage());
        Ability.create(new AbilityFrostbite());

        HashMap<Integer, Ability> torrentialRainAbilities = new HashMap<>();
        torrentialRainAbilities.put(0, Ability.get("Torrential Rain"));
        torrentialRainAbilities.put(1, Ability.get("Hailstorm Barrage"));
        torrentialRainAbilities.put(10, Ability.get("Frostbite"));
        AbilityTree.create("Torrential Rain", new AbilityTree(torrentialRainAbilities, List.of(0)));

        // Raigeki
        Ability.create(new AbilityRaigeki());
        Ability.create(new AbilityLightningStorm());

        HashMap<Integer, Ability> raigekiAbilities = new HashMap<>();
        raigekiAbilities.put(0, Ability.get("Raigeki"));
        raigekiAbilities.put(1, Ability.get("Lightning Storm"));
        AbilityTree.create("Raigeki", new AbilityTree(raigekiAbilities));

        // Whirlwind
        Ability.create(new AbilityWhirlwind());
        Ability.create(new AbilityWindShock());
        Ability.create(new AbilitySlashingWind());

        HashMap<Integer, Ability> whirlwindAbilities = new HashMap<>();
        whirlwindAbilities.put(0, Ability.get("Whirlwind"));
        whirlwindAbilities.put(1, Ability.get("Wind Shock"));
        whirlwindAbilities.put(10, Ability.get("Slashing Wind"));
        AbilityTree.create("Whirlwind", new AbilityTree(whirlwindAbilities, List.of(0)));

        Logger.log(LogLevel.DEBUG, "Registered a total of " + Ability.get().size() + " abilities.");

    }

}
