package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.active.air.*;
import net.toujoustudios.hyperspecies.ability.active.dark.AbilityEndblight;
import net.toujoustudios.hyperspecies.ability.active.dark.AbilityEngulfingDarkness;
import net.toujoustudios.hyperspecies.ability.active.dark.AbilityStrikeOfCorruption;
import net.toujoustudios.hyperspecies.ability.active.dark.AbilityTotalAnnihilation;
import net.toujoustudios.hyperspecies.ability.active.earth.*;
import net.toujoustudios.hyperspecies.ability.active.electro.*;
import net.toujoustudios.hyperspecies.ability.active.fairy.*;
import net.toujoustudios.hyperspecies.ability.active.fire.*;
import net.toujoustudios.hyperspecies.ability.active.flora.*;
import net.toujoustudios.hyperspecies.ability.active.psychic.AbilityPsychicWard;
import net.toujoustudios.hyperspecies.ability.active.psychic.AbilityTimothy;
import net.toujoustudios.hyperspecies.ability.active.water.*;
import net.toujoustudios.hyperspecies.ability.tree.Loadout;

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
        Loadout.create("Hellblight", new Loadout(hellblightAbilities));

        HashMap<Integer, Ability> endblightAbilities = new HashMap<>();
        endblightAbilities.put(0, Ability.getAbility("Endblight"));
        endblightAbilities.put(1, Ability.getAbility("Engulfing Darkness"));
        Loadout.create("Endblight", new Loadout(endblightAbilities));

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
        Loadout.create("Flame Shot", new Loadout(flameShotAbilities, List.of(0, 1)));

        HashMap<Integer, Ability> totalAnnihilationAbilities = new HashMap<>();
        totalAnnihilationAbilities.put(0, Ability.getAbility("Total Annihilation"));
        totalAnnihilationAbilities.put(1, Ability.getAbility("Strike Of Corruption"));
        Loadout.create("Total Annihilation", new Loadout(totalAnnihilationAbilities));

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
        Loadout.create("Enhancing Flame", new Loadout(enhancingFlameAbilities, List.of(0)));

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
        Loadout.create("Light Diffraction", new Loadout(lightDiffractionAbilities, List.of(0, 1)));

        // Healing Nature
        Ability.createAbility(new AbilityHealingNature());
        Ability.createAbility(new AbilityWoodlandsBlessing());

        HashMap<Integer, Ability> healingNatureAbilities = new HashMap<>();
        healingNatureAbilities.put(0, Ability.getAbility("Healing Nature"));
        healingNatureAbilities.put(1, Ability.getAbility("Woodlands Blessing"));
        Loadout.create("Healing Nature", new Loadout(healingNatureAbilities));

        // Thorn Seedling
        Ability.createAbility(new AbilityThornSeedling());
        Ability.createAbility(new AbilityRagingFlora());
        Ability.createAbility(new AbilityStunSpore());

        HashMap<Integer, Ability> thornSeedlingAbilities = new HashMap<>();
        thornSeedlingAbilities.put(0, Ability.getAbility("Thorn Seedling"));
        thornSeedlingAbilities.put(1, Ability.getAbility("Raging Flora"));
        thornSeedlingAbilities.put(2, Ability.getAbility("Stun Spore"));
        Loadout.create("Thorn Seedling", new Loadout(thornSeedlingAbilities));

        // Psychic Ward
        Ability.createAbility(new AbilityPsychicWard());
        Ability.createAbility(new AbilityTimothy());

        HashMap<Integer, Ability> psychicWardAbilities = new HashMap<>();
        psychicWardAbilities.put(0, Ability.getAbility("Psychic Ward"));
        psychicWardAbilities.put(1, Ability.getAbility("Timothy"));
        Loadout.create("Psychic Ward", new Loadout(psychicWardAbilities));

        // Quick Growth
        Ability.createAbility(new AbilityQuickGrowth());
        Ability.createAbility(new AbilityFertilizingRain());

        HashMap<Integer, Ability> quickGrowthAbilities = new HashMap<>();
        quickGrowthAbilities.put(0, Ability.getAbility("Quick Growth"));
        quickGrowthAbilities.put(1, Ability.getAbility("Fertilizing Rain"));
        Loadout.create("Quick Growth", new Loadout(quickGrowthAbilities));

        // Flash
        Ability.createAbility(new AbilityFlash());
        Ability.createAbility(new AbilityLightspeed());

        HashMap<Integer, Ability> flashAbilities = new HashMap<>();
        flashAbilities.put(0, Ability.getAbility("Flash"));
        flashAbilities.put(1, Ability.getAbility("Lightspeed"));
        Loadout.create("Flash", new Loadout(flashAbilities));

        // Supersonic Speed
        Ability.createAbility(new AbilitySupersonicSpeed());
        Ability.createAbility(new AbilityICBM());

        HashMap<Integer, Ability> supersonicSpeedAbilities = new HashMap<>();
        supersonicSpeedAbilities.put(0, Ability.getAbility("Supersonic Speed"));
        supersonicSpeedAbilities.put(1, Ability.getAbility("ICBM"));
        Loadout.create("Supersonic Speed", new Loadout(supersonicSpeedAbilities));

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
        Loadout.create("Sharp Stone", new Loadout(sharpStoneAbilities));

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
        Loadout.create("Mine Sight", new Loadout(mineSightAbilities, List.of(0)));

        // Stone Born
        Ability.createAbility(new AbilityStoneBorn());
        Ability.createAbility(new AbilityBornIntoStone());

        HashMap<Integer, Ability> stoneBornAbilities = new HashMap<>();
        stoneBornAbilities.put(0, Ability.getAbility("Stone Born"));
        stoneBornAbilities.put(1, Ability.getAbility("Born Into Stone"));
        Loadout.create("Stone Born", new Loadout(stoneBornAbilities));

        // Dash
        Ability.createAbility(new AbilityDash());
        Ability.createAbility(new AbilitySilentDash());

        HashMap<Integer, Ability> dashAbilities = new HashMap<>();
        dashAbilities.put(0, Ability.getAbility("Dash"));
        dashAbilities.put(1, Ability.getAbility("Silent Dash"));
        Loadout.create("Dash", new Loadout(dashAbilities));

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
        Loadout.create("Aquatic Surge", new Loadout(aquaticSurgeAbilities, List.of(0)));

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
        Loadout.create("Healing Waters", new Loadout(healingWatersAbilities, List.of(0)));

        // Torrential Rain
        Ability.createAbility(new AbilityTorrentialRain());
        Ability.createAbility(new AbilityHailstormBarrage());
        Ability.createAbility(new AbilityFrostbite());

        HashMap<Integer, Ability> torrentialRainAbilities = new HashMap<>();
        torrentialRainAbilities.put(0, Ability.getAbility("Torrential Rain"));
        torrentialRainAbilities.put(1, Ability.getAbility("Hailstorm Barrage"));
        torrentialRainAbilities.put(10, Ability.getAbility("Frostbite"));
        Loadout.create("Torrential Rain", new Loadout(torrentialRainAbilities, List.of(0)));

        // Raigeki
        Ability.createAbility(new AbilityRaigeki());
        Ability.createAbility(new AbilityLightningStorm());

        HashMap<Integer, Ability> raigekiAbilities = new HashMap<>();
        raigekiAbilities.put(0, Ability.getAbility("Raigeki"));
        raigekiAbilities.put(1, Ability.getAbility("Lightning Storm"));
        Loadout.create("Raigeki", new Loadout(raigekiAbilities));

        // Whirlwind
        Ability.createAbility(new AbilityWhirlwind());
        Ability.createAbility(new AbilityWindShock());
        Ability.createAbility(new AbilitySlashingWind());

        HashMap<Integer, Ability> whirlwindAbilities = new HashMap<>();
        whirlwindAbilities.put(0, Ability.getAbility("Whirlwind"));
        whirlwindAbilities.put(1, Ability.getAbility("Wind Shock"));
        whirlwindAbilities.put(10, Ability.getAbility("Slashing Wind"));
        Loadout.create("Whirlwind", new Loadout(whirlwindAbilities, List.of(0)));

    }

}
