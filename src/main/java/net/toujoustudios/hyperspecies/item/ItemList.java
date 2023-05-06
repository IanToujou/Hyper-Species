package net.toujoustudios.hyperspecies.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemList {

    public static ItemStack FILLER = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    public static ItemStack NEXT_PAGE = new ItemStack(Material.PLAYER_HEAD);
    public static ItemStack PREVIOUS_PAGE = new ItemStack(Material.PLAYER_HEAD);
    public static ItemStack CONFIRM = new ItemStack(Material.GREEN_CONCRETE);
    public static ItemStack CANCEL = new ItemStack(Material.RED_CONCRETE);
    public static ItemStack DEMON = new ItemStack(Material.FIRE_CHARGE);
    public static ItemStack DEMON_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack DEMON_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack DEMON_ABILITY_ENHANCING_FLAME = new ItemStack(Material.BLAZE_POWDER);
    public static ItemStack DEMON_ABILITY_HELLBLIGHT = new ItemStack(Material.DRIED_KELP);
    public static ItemStack DEMON_ABILITY_DEMONIC_RAGE = new ItemStack(Material.FERMENTED_SPIDER_EYE);
    public static ItemStack DEMON_ABILITY_METEOR_STRIKE = new ItemStack(Material.FIRE_CHARGE);
    public static ItemStack DEMON_ABILITY_RAGING_BURST = new ItemStack(Material.MAGMA_CREAM);
    public static ItemStack DEMON_ABILITY_MAGMATIC_DETONATION = new ItemStack(Material.MAGMA_BLOCK);
    public static ItemStack DEMON_ABILITY_FOCUS = new ItemStack(Material.SPYGLASS);
    public static ItemStack DEMON_ABILITY_STRIKE_OF_CORRUPTION = new ItemStack(Material.CROSSBOW);
    public static ItemStack ELF = new ItemStack(Material.SWEET_BERRIES);
    public static ItemStack ELF_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack ELF_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack ELF_ABILITY_HEALING_NATURE = new ItemStack(Material.SWEET_BERRIES);
    public static ItemStack ELF_ABILITY_THORN_SEEDLING = new ItemStack(Material.JUNGLE_SAPLING);
    public static ItemStack ELF_ABILITY_RAGING_FLORA = new ItemStack(Material.CHORUS_FLOWER);
    public static ItemStack ELF_ABILITY_GREEN_HELL = new ItemStack(Material.HONEYCOMB);
    public static ItemStack ELF_ABILITY_WOODLANDS_BLESSING = new ItemStack(Material.GLOW_BERRIES);
    public static ItemStack ELF_ABILITY_ROOTS_OF_NATURE = new ItemStack(Material.MANGROVE_PROPAGULE);
    public static ItemStack ELF_ABILITY_MANA_POOL = new ItemStack(Material.PRISMARINE_CRYSTALS);
    public static ItemStack ELF_ABILITY_PROTECT_THE_HOMELAND = new ItemStack(Material.SHIELD);
    public static ItemStack REPTILE = new ItemStack(Material.NAUTILUS_SHELL);
    public static ItemStack REPTILE_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack REPTILE_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack ANGEL = new ItemStack(Material.TOTEM_OF_UNDYING);
    public static ItemStack ANGEL_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack ANGEL_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack HUMAN = new ItemStack(Material.MINECART);
    public static ItemStack HUMAN_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack HUMAN_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack AQUATILIA = new ItemStack(Material.HEART_OF_THE_SEA);
    public static ItemStack AQUATILIA_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack AQUATILIA_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack FELINE = new ItemStack(Material.PHANTOM_MEMBRANE);
    public static ItemStack FELINE_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack FELINE_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack DWARF = new ItemStack(Material.RAW_GOLD);
    public static ItemStack DWARF_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack DWARF_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack WOLF = new ItemStack(Material.BONE);
    public static ItemStack WOLF_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack WOLF_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);

    @SuppressWarnings("all")
    public static void initialize() {

        ItemMeta fillerMeta = FILLER.getItemMeta();
        fillerMeta.setDisplayName(" ");
        FILLER.setItemMeta(fillerMeta);

        SkullMeta nextPageMeta = (SkullMeta) NEXT_PAGE.getItemMeta();
        nextPageMeta.setOwner("MHF_ArrowRight");
        nextPageMeta.setDisplayName("§eNext Page");
        NEXT_PAGE.setItemMeta(nextPageMeta);

        SkullMeta previousPageMeta = (SkullMeta) PREVIOUS_PAGE.getItemMeta();
        previousPageMeta.setOwner("MHF_ArrowLeft");
        previousPageMeta.setDisplayName("§ePrevious Page");
        PREVIOUS_PAGE.setItemMeta(previousPageMeta);

        ItemMeta confirmMeta = CONFIRM.getItemMeta();
        confirmMeta.setDisplayName("§aConfirm");
        CONFIRM.setItemMeta(confirmMeta);

        ItemMeta cancelMeta = CANCEL.getItemMeta();
        cancelMeta.setDisplayName("§cCancel");
        CANCEL.setItemMeta(cancelMeta);

        // DEMON

        ItemMeta demonMeta = DEMON.getItemMeta();
        demonMeta.setDisplayName("§6Demon");
        demonMeta.setLore(List.of(
                "§7The rising conflicts between heaven and",
                "§7hell have left a deep hatred of angels in",
                "§7the demon's civilization. As a natural",
                "§7counterpart to the angels, demons cast dark",
                "§7magic to influence the battle field.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        DEMON.setItemMeta(demonMeta);

        ItemMeta demonPassiveAbilitiesMeta = DEMON_PASSIVE_ABILITIES.getItemMeta();
        demonPassiveAbilitiesMeta.setDisplayName("§6Passive Abilities");
        demonPassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Fire resistance",
                "§7[§a+§7] Night vision",
                "§7[§a+§7] Speed at night",
                "",
                "§7[§c-§7] Slowness underwater",
                "§7[§c-§7] Hunger underwater",
                "§7[§c-§7] Weakness during the day"
        ));
        DEMON_PASSIVE_ABILITIES.setItemMeta(demonPassiveAbilitiesMeta);

        ItemMeta demonActiveAbilitiesMeta = DEMON_ACTIVE_ABILITIES.getItemMeta();
        demonActiveAbilitiesMeta.setDisplayName("§6Active Abilities");
        demonActiveAbilitiesMeta.setLore(List.of(
                "§eBase Abilities:",
                "§9▶ §7Enhancing Flame",
                "§9▶ §7Hellblight",
                "§9▶ §7Demonic Rage",
                "§9▶ §7Meteor Strike",
                "",
                "§bHellspawn §eSubclass:",
                "§9▶ §7Raging Burst",
                "§9▶ §7Magmatic Detonation",
                "",
                "§bFallen Angel §eSubclass:",
                "§9▶ §7Focus",
                "§9▶ §7Strike of Corruption",
                "",
                "§b▶ Click to find out more!"
        ));
        DEMON_ACTIVE_ABILITIES.setItemMeta(demonActiveAbilitiesMeta);

        ItemMeta demonAbilityEnhancingFlameMeta = DEMON_ABILITY_ENHANCING_FLAME.getItemMeta();
        demonAbilityEnhancingFlameMeta.setDisplayName("§6Enhancing Flame");
        demonAbilityEnhancingFlameMeta.setLore(List.of(
                "§8Effect Ability",
                "",
                "§7Gain fire aspect for a short amount of time,",
                "§7to light your enemies on fire.",
                "",
                "§7Cost: §b5 Mana",
                "§7Delay: §e90 Seconds"
        ));
        DEMON_ABILITY_ENHANCING_FLAME.setItemMeta(demonAbilityEnhancingFlameMeta);

        ItemMeta demonAbilityHellblightMeta = DEMON_ABILITY_HELLBLIGHT.getItemMeta();
        demonAbilityHellblightMeta.setDisplayName("§6Hellblight");
        demonAbilityHellblightMeta.setLore(List.of(
                "§8Effect Ability",
                "",
                "§7Cast an AoE attack that slightly damages",
                "§7enemies in the radius, additionally giving",
                "§7them slowness.",
                "",
                "§7Cost: §b6 Mana",
                "§7Delay: §e60 Seconds"
        ));
        DEMON_ABILITY_HELLBLIGHT.setItemMeta(demonAbilityHellblightMeta);

        ItemMeta demonAbilityDemonicRageMeta = DEMON_ABILITY_DEMONIC_RAGE.getItemMeta();
        demonAbilityDemonicRageMeta.setDisplayName("§6Demonic Rage");
        demonAbilityDemonicRageMeta.setLore(List.of(
                "§8Effect Ability",
                "",
                "§7Gain a small strength buff and regenerate",
                "§7mana when you deal damage to an enemy.",
                "",
                "§7Cost: §b10 Mana",
                "§7Delay: §e120 Seconds"
        ));
        DEMON_ABILITY_DEMONIC_RAGE.setItemMeta(demonAbilityDemonicRageMeta);

        ItemMeta demonAbilityMeteorStrikeMeta = DEMON_ABILITY_METEOR_STRIKE.getItemMeta();
        demonAbilityMeteorStrikeMeta.setDisplayName("§6Meteor Strike");
        demonAbilityMeteorStrikeMeta.setLore(List.of(
                "§cUltimate §8Damage Ability",
                "",
                "§7Call in a meteor strike that does heavy",
                "§7damage to enemies in a small perimeter.",
                "",
                "§7Cost: §b12 Mana",
                "§7Delay: §e180 Seconds"
        ));
        DEMON_ABILITY_METEOR_STRIKE.setItemMeta(demonAbilityMeteorStrikeMeta);

        ItemMeta demonAbilityRagingBurstMeta = DEMON_ABILITY_RAGING_BURST.getItemMeta();
        demonAbilityRagingBurstMeta.setDisplayName("§6Raging Burst");
        demonAbilityRagingBurstMeta.setLore(List.of(
                "§8Available in the §dHellspawn§8 subclass.",
                "§8Effect Ability",
                "",
                "§7Can only be used once you get at least one",
                "§7kill with demonic rage. Applies even a stronger",
                "§7buff and further strength.",
                "",
                "§7Cost: §b8 Mana",
                "§7Delay: §e220 Seconds"
        ));
        DEMON_ABILITY_RAGING_BURST.setItemMeta(demonAbilityRagingBurstMeta);

        ItemMeta demonAbilityMagmaticDetonation = DEMON_ABILITY_MAGMATIC_DETONATION.getItemMeta();
        demonAbilityMagmaticDetonation.setDisplayName("§6Magmatic Detonation");
        demonAbilityMagmaticDetonation.setLore(List.of(
                "§8Available in the §dHellspawn§8 subclass.",
                "§cUltimate §8Damage Ability",
                "",
                "§7Summon magma blocks that explode after a",
                "§7certain time or if a player touches them.",
                "",
                "§7Cost: §b14 Mana",
                "§7Delay: §e180 Seconds"
        ));
        DEMON_ABILITY_MAGMATIC_DETONATION.setItemMeta(demonAbilityMagmaticDetonation);

        ItemMeta demonAbilityFocus = DEMON_ABILITY_FOCUS.getItemMeta();
        demonAbilityFocus.setDisplayName("§6Focus");
        demonAbilityFocus.setLore(List.of(
                "§8Available in the §dFallen Angel§8 subclass.",
                "§8Effect Ability",
                "",
                "§7The focues enemy will be seen through walls",
                "§7by the attacking team.",
                "",
                "§7Cost: §b6 Mana",
                "§7Delay: §e120 Seconds"
        ));
        DEMON_ABILITY_FOCUS.setItemMeta(demonAbilityFocus);

        ItemMeta demonAbilityStrikeOfCorruptionMeta = DEMON_ABILITY_STRIKE_OF_CORRUPTION.getItemMeta();
        demonAbilityStrikeOfCorruptionMeta.setDisplayName("§6Strike of Corruption");
        demonAbilityStrikeOfCorruptionMeta.setLore(List.of(
                "§8Available in the §dFallen Angel§8 subclass.",
                "§cUltimate §8Damage Ability",
                "",
                "§7Fire a highly concentrated beam and fire it",
                "§7at one player, dealing a huge amount of damage.",
                "",
                "§7Cost: §b16 Mana",
                "§7Delay: §e300 Seconds"
        ));
        DEMON_ABILITY_STRIKE_OF_CORRUPTION.setItemMeta(demonAbilityStrikeOfCorruptionMeta);

        // ELF

        ItemMeta elfMeta = ELF.getItemMeta();
        elfMeta.setDisplayName("§6Elf");
        elfMeta.setLore(List.of(
                "§7Elves are a species that live deep within",
                "§7the woods, using magic to fight and",
                "§7protect their homeland against the thread",
                "§7of humanity.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        ELF.setItemMeta(elfMeta);

        ItemMeta elfPassiveAbilitiesMeta = ELF_PASSIVE_ABILITIES.getItemMeta();
        elfPassiveAbilitiesMeta.setDisplayName("§6Passive Abilities");
        elfPassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Regeneration in green biomes",
                "§7[§a+§7] Mana regeneration on diorite",
                "§7[§a+§7] Strength on wood blocks",
                "",
                "§7[§c-§7] Weakness and slowness on granite",
                "§7[§c-§7] Cannot eat meat",
                "§7[§c-§7] Freeze in snow biomes"
        ));
        ELF_PASSIVE_ABILITIES.setItemMeta(elfPassiveAbilitiesMeta);

        ItemMeta elfActiveAbilitiesMeta = ELF_ACTIVE_ABILITIES.getItemMeta();
        elfActiveAbilitiesMeta.setDisplayName("§6Active Abilities");
        elfActiveAbilitiesMeta.setLore(List.of(
                "§eBase Abilities:",
                "§9▶ §7Healing Nature",
                "§9▶ §7Thorn Seedling",
                "§9▶ §7Raging Flora",
                "§9▶ §7Green Hell",
                "",
                "§bDryad §eSubclass:",
                "§9▶ §7Woodland's Blessing",
                "§9▶ §7Roots of Nature",
                "",
                "§bGuardian §eSubclass:",
                "§9▶ §7Mana Pool",
                "§9▶ §7Protect the Homeland",
                "",
                "§b▶ Click to find out more!"
        ));
        ELF_ACTIVE_ABILITIES.setItemMeta(elfActiveAbilitiesMeta);

        ItemMeta elfAbilityHealingNatureMeta = ELF_ABILITY_HEALING_NATURE.getItemMeta();
        elfAbilityHealingNatureMeta.setDisplayName("§6Healing Nature");
        elfAbilityHealingNatureMeta.setLore(List.of(
                "§8Support Ability",
                "",
                "§7Applies light regeneration to an ally.",
                "",
                "§7Cost: §b4 Mana",
                "§7Delay: §e60 Seconds"
        ));
        ELF_ABILITY_HEALING_NATURE.setItemMeta(elfAbilityHealingNatureMeta);

        ItemMeta elfAbilityThornSeedlingMeta = ELF_ABILITY_THORN_SEEDLING.getItemMeta();
        elfAbilityThornSeedlingMeta.setDisplayName("§6Thorn Seedling");
        elfAbilityThornSeedlingMeta.setLore(List.of(
                "§8Damage Ability",
                "",
                "§7Summons a plant that applies poison to",
                "§7passing enemies.",
                "",
                "§7Cost: §b8 Mana",
                "§7Delay: §e120 Seconds"
        ));
        ELF_ABILITY_THORN_SEEDLING.setItemMeta(elfAbilityThornSeedlingMeta);

        ItemMeta elfAbilityRagingFloraMeta = ELF_ABILITY_RAGING_FLORA.getItemMeta();
        elfAbilityRagingFloraMeta.setDisplayName("§6Raging Flora");
        elfAbilityRagingFloraMeta.setLore(List.of(
                "§8Effect Ability",
                "",
                "§7Summons a plant that buffs nearby allies,",
                "§7giving them strength.",
                "",
                "§7Cost: §b10 Mana",
                "§7Delay: §e150 Seconds"
        ));
        ELF_ABILITY_RAGING_FLORA.setItemMeta(elfAbilityRagingFloraMeta);

        ItemMeta elfAbilityGreenHellMeta = ELF_ABILITY_GREEN_HELL.getItemMeta();
        elfAbilityGreenHellMeta.setDisplayName("§6Green Hell");
        elfAbilityGreenHellMeta.setLore(List.of(
                "§cUltimate §8Damage Ability",
                "",
                "§7Summons a plant that will attack enemies,",
                "§7stunning them if they are hit.",
                "",
                "§7Cost: §b14 Mana",
                "§7Delay: §e220 Seconds"
        ));
        ELF_ABILITY_GREEN_HELL.setItemMeta(elfAbilityGreenHellMeta);

        // REPTILE

        ItemMeta reptileMeta = REPTILE.getItemMeta();
        reptileMeta.setDisplayName("§6Reptile");
        reptileMeta.setLore(List.of(
                "§7Reptiles are mid-speed and mid-armor",
                "§7species that live either on the water",
                "§7or in the desert.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        REPTILE.setItemMeta(reptileMeta);

        // ANGEL

        ItemMeta angelMeta = ANGEL.getItemMeta();
        angelMeta.setDisplayName("§6Angel");
        angelMeta.setLore(List.of(
                "§7Protecting humanity and its own kind have",
                "§7lead the angels to start a war against the",
                "§7demons. Angels have powerful single target",
                "§7abilities to obliterate strong foes.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        ANGEL.setItemMeta(angelMeta);

        ItemMeta angelPassiveAbilitiesMeta = ANGEL_PASSIVE_ABILITIES.getItemMeta();
        angelPassiveAbilitiesMeta.setDisplayName("§6Passive Abilities");
        angelPassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Slow falling when pressing shift",
                "§7[§a+§7] Resistance in sunlight",
                "§7[§a+§7] Regeneration in sunlight",
                "",
                "§7[§c-§7] Weakness at night",
                "§7[§c-§7] Slowness underwater",
                "§7[§c-§7] Hunger underwater"
        ));
        ANGEL_PASSIVE_ABILITIES.setItemMeta(angelPassiveAbilitiesMeta);

        ItemMeta angelActiveAbilitiesMeta = ANGEL_ACTIVE_ABILITIES.getItemMeta();
        angelActiveAbilitiesMeta.setDisplayName("§6Active Abilities");
        angelActiveAbilitiesMeta.setLore(List.of(
                "§eBase Abilities:",
                "§9▶ §7Holy Momentum",
                "§9▶ §7Followers Blessing",
                "§9▶ §7Aether Minion",
                "§9▶ §7Healing Aura",
                "",
                "§bArch Angel §eSubclass:",
                "§9▶ §7Sacred Combo",
                "§9▶ §7Blessed Incursion",
                "",
                "§bDemi God §eSubclass:",
                "§9▶ §7Focus",
                "§9▶ §7Divine Strike",
                "",
                "§b▶ Click to find out more!"
        ));
        ANGEL_ACTIVE_ABILITIES.setItemMeta(angelActiveAbilitiesMeta);

        // HUMAN

        ItemMeta humanMeta = HUMAN.getItemMeta();
        humanMeta.setDisplayName("§6Human");
        humanMeta.setLore(List.of(
                "§7Humans are boring you say? Humanity has come",
                "§7a long way, buildingcities, developing technologies",
                "§7and going to war.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        HUMAN.setItemMeta(humanMeta);

        // AQUATILIA

        ItemMeta seaCreatureMeta = AQUATILIA.getItemMeta();
        seaCreatureMeta.setDisplayName("§6Aquatilia");
        seaCreatureMeta.setLore(List.of(
                "§7Sea creatures live under the sea, using",
                "§7their environment to their advance. They",
                "§7gain underwater vision, haste, speed.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        AQUATILIA.setItemMeta(seaCreatureMeta);

        // FELINE

        ItemMeta felineMeta = FELINE.getItemMeta();
        felineMeta.setDisplayName("§6Feline");
        felineMeta.setLore(List.of(
                "§7The feline species may not seem too",
                "§7dangerous, but the fast abilities and",
                "§7team fight properties should not be",
                "§7undererstimated.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        FELINE.setItemMeta(felineMeta);

        // DWARF

        ItemMeta dwarfMeta = DWARF.getItemMeta();
        dwarfMeta.setDisplayName("§6Dwarf");
        dwarfMeta.setLore(List.of(
                "§7Dwarves are highly specialized on mining",
                "§7and living their life underground. Altough",
                "§7they are smaller, never under-estimate a",
                "§7dwarf's thoughness.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        DWARF.setItemMeta(dwarfMeta);

        // WOLF

        ItemMeta wolfMeta = WOLF.getItemMeta();
        wolfMeta.setDisplayName("§6Wolf");
        wolfMeta.setLore(List.of(
                "§7Wolves live in isolated environments, but",
                "§7they can withstand extreme temperatures.",
                "§7They are also specialized on life steal.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        WOLF.setItemMeta(wolfMeta);

    }

}
