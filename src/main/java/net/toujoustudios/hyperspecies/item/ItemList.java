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
    public static ItemStack ELF = new ItemStack(Material.SWEET_BERRIES);
    public static ItemStack REPTILE = new ItemStack(Material.NAUTILUS_SHELL);
    public static ItemStack ANGEL = new ItemStack(Material.TOTEM_OF_UNDYING);
    public static ItemStack ANGEL_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack ANGEL_ACTIVE_ABILITIES = new ItemStack(Material.ENDER_EYE);
    public static ItemStack HUMAN = new ItemStack(Material.MINECART);
    public static ItemStack SEA_CREATURE = new ItemStack(Material.HEART_OF_THE_SEA);
    public static ItemStack FELINE = new ItemStack(Material.PHANTOM_MEMBRANE);
    public static ItemStack DWARF = new ItemStack(Material.GOLD_INGOT);
    public static ItemStack WOLF = new ItemStack(Material.BONE);

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
                "§7[§c-§7] Slowness underwater",
                "§7[§c-§7] Hunger underwater",
                "§7[§c-§7] Weaknight during the day"
        ));
        DEMON_PASSIVE_ABILITIES.setItemMeta(demonPassiveAbilitiesMeta);

        ItemMeta demonActiveAbilitiesMeta = DEMON_ACTIVE_ABILITIES.getItemMeta();
        demonActiveAbilitiesMeta.setDisplayName("§6Active Abilities");
        demonActiveAbilitiesMeta.setLore(List.of(
                "§eBase Abilities:",
                "§9▶ §7Hellblight",
                "§9▶ §7Enhancing Flame",
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
                "§7[§c-§7] Weakness at night",
                "§7[§c-§7] Slowness underwater",
                "§7[§c-§7] Hunger underwater"
        ));
        ANGEL_PASSIVE_ABILITIES.setItemMeta(angelPassiveAbilitiesMeta);

        ItemMeta angelActiveAbilitiesMeta = ANGEL_ACTIVE_ABILITIES.getItemMeta();
        angelActiveAbilitiesMeta.setDisplayName("§6Active Abilities");
        angelActiveAbilitiesMeta.setLore(List.of(
                "§d▶ §7Hellblight",
                "§d▶ §7Enhancing Flame",
                "§d▶ §7Demonic Rage"
        ));
        ANGEL_ACTIVE_ABILITIES.setItemMeta(angelActiveAbilitiesMeta);

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

        ItemMeta seaCreatureMeta = SEA_CREATURE.getItemMeta();
        seaCreatureMeta.setDisplayName("§6Sea Creature");
        seaCreatureMeta.setLore(List.of(
                "§7Sea creatures live under the sea, using",
                "§7their environment to their advance. They",
                "§7gain underwater vision, haste, speed.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        SEA_CREATURE.setItemMeta(seaCreatureMeta);

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
