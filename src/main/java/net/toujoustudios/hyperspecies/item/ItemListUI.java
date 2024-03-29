package net.toujoustudios.hyperspecies.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemListUI {

    public static ItemStack FILLER = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    public static ItemStack NEXT = new ItemStack(Material.PLAYER_HEAD);
    public static ItemStack PREVIOUS = new ItemStack(Material.PLAYER_HEAD);
    public static ItemStack CONFIRM = new ItemStack(Material.GREEN_CONCRETE);
    public static ItemStack CANCEL = new ItemStack(Material.BARRIER);
    public static ItemStack DEMON = new ItemStack(Material.FIRE_CHARGE);
    public static ItemStack DEMON_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack ELF = new ItemStack(Material.SWEET_BERRIES);
    public static ItemStack ELF_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack REPTILE = new ItemStack(Material.NAUTILUS_SHELL);
    public static ItemStack REPTILE_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack ANGEL = new ItemStack(Material.TOTEM_OF_UNDYING);
    public static ItemStack ANGEL_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack HUMAN = new ItemStack(Material.MINECART);
    public static ItemStack HUMAN_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack AQUATILIA = new ItemStack(Material.HEART_OF_THE_SEA);
    public static ItemStack AQUATILIA_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack FELINE = new ItemStack(Material.PHANTOM_MEMBRANE);
    public static ItemStack FELINE_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack DWARF = new ItemStack(Material.RAW_GOLD);
    public static ItemStack DWARF_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack WOLF = new ItemStack(Material.BONE);
    public static ItemStack WOLF_PASSIVE_ABILITIES = new ItemStack(Material.ENDER_PEARL);
    public static ItemStack TEAM_BROWSE = new ItemStack(Material.ENDER_EYE);
    public static ItemStack TEAM_CREATE = new ItemStack(Material.NETHER_STAR);
    public static ItemStack TEAM_SETTINGS = new ItemStack(Material.REDSTONE);
    public static ItemStack TEAM_CHANGE_NAME = new ItemStack(Material.NAME_TAG);
    public static ItemStack TEAM_CHANGE_DESCRIPTION = new ItemStack(Material.MINECART);
    public static ItemStack TEAM_CHANGE_STATUS = new ItemStack(Material.PURPLE_DYE);
    public static ItemStack TEAM_CHANGE_STATUS_CLOSED = new ItemStack(Material.GRAY_DYE);
    public static ItemStack TEAM_CHANGE_STATUS_INVITE = new ItemStack(Material.PURPLE_DYE);
    public static ItemStack TEAM_CHANGE_STATUS_OPEN = new ItemStack(Material.LIME_DYE);
    public static ItemStack TEAM_LEAVE = new ItemStack(Material.PUFFERFISH);
    public static ItemStack TEAM_LEAVE_CONFIRM = new ItemStack(Material.PUFFERFISH);
    public static ItemStack TEAM_KICK_PLAYER = new ItemStack(Material.FIRE_CHARGE);
    public static ItemStack TEAM_PROMOTE_PLAYER = new ItemStack(Material.MAGMA_CREAM);
    public static ItemStack RESET_CHARACTER = new ItemStack(Material.RED_CONCRETE);
    public static ItemStack RESET_CHARACTER_CONFIRM = new ItemStack(Material.REDSTONE_BLOCK);
    public static ItemStack TREE_TRACK_LOCKED = new ItemStack(Material.GRAY_DYE);
    public static ItemStack TREE_TRACK_UNLOCKED = new ItemStack(Material.GREEN_DYE);
    public static ItemStack TREE_RESET = new ItemStack(Material.TNT);
    public static ItemStack TREE_HELP = new ItemStack(Material.NAME_TAG);
    public static ItemStack CHARACTER_CHANGE_NAME = new ItemStack(Material.NAME_TAG);

    @SuppressWarnings("all")
    public static void initialize() {

        ItemMeta fillerMeta = FILLER.getItemMeta();
        fillerMeta.setDisplayName(" ");
        FILLER.setItemMeta(fillerMeta);

        SkullMeta nextMeta = (SkullMeta) NEXT.getItemMeta();
        nextMeta.setOwner("MHF_ArrowRight");
        nextMeta.setDisplayName("§eNext");
        NEXT.setItemMeta(nextMeta);

        SkullMeta previousMeta = (SkullMeta) PREVIOUS.getItemMeta();
        previousMeta.setOwner("MHF_ArrowLeft");
        previousMeta.setDisplayName("§eBack");
        PREVIOUS.setItemMeta(previousMeta);

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
                "§7[§a+§7] Vision & speed at night",
                "§7[§a+§7] Strength on netherrack",
                "",
                "§7[§c-§7] Weak & slow in water",
                "§7[§c-§7] Damage in sunlight",
                "§7[§c-§7] Mana drain on soul sand"
        ));
        DEMON_PASSIVE_ABILITIES.setItemMeta(demonPassiveAbilitiesMeta);

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
                "§7[§a+§7] Regeneration in forests",
                "§7[§a+§7] Mana regeneration on leaves",
                "§7[§a+§7] Strength on wood blocks",
                "",
                "§7[§c-§7] Weak and slown on granite",
                "§7[§c-§7] Cannot eat meat",
                "§7[§c-§7] Freeze in cold biomes"
        ));
        ELF_PASSIVE_ABILITIES.setItemMeta(elfPassiveAbilitiesMeta);

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

        ItemMeta reptilePassiveAbilitiesMeta = REPTILE_PASSIVE_ABILITIES.getItemMeta();
        reptilePassiveAbilitiesMeta.setDisplayName("§6Passive Abilities");
        reptilePassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Speed boost in swamps, deserts and water",
                "§7[§a+§7] Resistance",
                "§7[§a+§7] No fall damage",
                "",
                "§7[§c-§7] Slowness in cold biomes",
                "§7[§c-§7] Cannot gain shields from other classes",
                "§7[§c-§7] Weakness at night"
        ));
        REPTILE_PASSIVE_ABILITIES.setItemMeta(reptilePassiveAbilitiesMeta);

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
                "§7[§a+§7] Regeneration in sunlight",
                "§7[§a+§7] Mana regeneration on gold",
                "",
                "§7[§c-§7] Slowness in water",
                "§7[§c-§7] Weakness at night",
                "§7[§c-§7] Cannot survive in nether"
        ));
        ANGEL_PASSIVE_ABILITIES.setItemMeta(angelPassiveAbilitiesMeta);

        // HUMAN

        ItemMeta humanMeta = HUMAN.getItemMeta();
        humanMeta.setDisplayName("§6Human");
        humanMeta.setLore(List.of(
                "§7Humans are boring you say? Humanity has come",
                "§7a long way, building cities, developing technologies",
                "§7and going to war.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        HUMAN.setItemMeta(humanMeta);

        ItemMeta humanPassiveAbilitiesMeta = HUMAN_PASSIVE_ABILITIES.getItemMeta();
        humanPassiveAbilitiesMeta.setDisplayName("§6Passive Abilities");
        humanPassiveAbilitiesMeta.setLore(List.of(
                "§7No passive abilities."
        ));
        HUMAN_PASSIVE_ABILITIES.setItemMeta(humanPassiveAbilitiesMeta);

        // AQUATILIA

        ItemMeta aquatiliaMeta = AQUATILIA.getItemMeta();
        aquatiliaMeta.setDisplayName("§6Aquatilia");
        aquatiliaMeta.setLore(List.of(
                "§7Aquatilia live under the sea, using",
                "§7their environment to their advance. They",
                "§7gain underwater vision, haste, speed.",
                "§r",
                "§b▶ Click to find out more!"
        ));
        AQUATILIA.setItemMeta(aquatiliaMeta);

        ItemMeta aquatiliaPassiveAbilitiesMeta = AQUATILIA_PASSIVE_ABILITIES.getItemMeta();
        aquatiliaPassiveAbilitiesMeta.setDisplayName("§6Passive Abilities");
        aquatiliaPassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Haste, speed and breathing underwater",
                "§7[§a+§7] Night vision",
                "§7[§a+§7] Strength and regeneration on corals",
                "",
                "§7[§c-§7] Damage in sunlight",
                "§7[§c-§7] Slowness on land",
                "§7[§c-§7] Strong damage in dry biomes"
        ));
        AQUATILIA_PASSIVE_ABILITIES.setItemMeta(aquatiliaPassiveAbilitiesMeta);

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

        ItemMeta felinePassiveAbilitiesMeta = FELINE_PASSIVE_ABILITIES.getItemMeta();
        felinePassiveAbilitiesMeta.setDisplayName("§6Feline Abilities");
        felinePassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Permanent night vision",
                "§7[§a+§7] Permanent speed",
                "§7[§a+§7] No fall damage",
                "",
                "§7[§c-§7] Can't mine in water",
                "§7[§c-§7] Cannot eat plants",
                "§7[§c-§7] Slow and weak in tight places"
        ));
        FELINE_PASSIVE_ABILITIES.setItemMeta(felinePassiveAbilitiesMeta);

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

        ItemMeta dwarfPassiveAbilitiesMeta = DWARF_PASSIVE_ABILITIES.getItemMeta();
        dwarfPassiveAbilitiesMeta.setDisplayName("§6Dwarf Abilities");
        dwarfPassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Permanent haste",
                "§7[§a+§7] Permanent night vision",
                "§7[§a+§7] Strength & fire resistance if drunk",
                "",
                "§7[§c-§7] Mining fatigue in water",
                "§7[§c-§7] Weakness if sober",
                "§7[§c-§7] Permanent slowness"
        ));
        DWARF_PASSIVE_ABILITIES.setItemMeta(dwarfPassiveAbilitiesMeta);

        ItemMeta wolfPassiveAbilitiesMeta = WOLF_PASSIVE_ABILITIES.getItemMeta();
        wolfPassiveAbilitiesMeta.setDisplayName("§6Wolf Abilities");
        wolfPassiveAbilitiesMeta.setLore(List.of(
                "§7[§a+§7] Double damage on low HP targets",
                "§7[§a+§7] Vision and speed at night",
                "§7[§a+§7] Detect nearby players while sneaking",
                "",
                "§7[§c-§7] Take more damage if no wolf nearby",
                "§7[§c-§7] Nausea in sunlight",
                "§7[§c-§7] Can only eat meat"
        ));
        WOLF_PASSIVE_ABILITIES.setItemMeta(wolfPassiveAbilitiesMeta);

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

        // OTHER

        ItemMeta teamBrowseMeta = TEAM_BROWSE.getItemMeta();
        teamBrowseMeta.setDisplayName("§eBrowse Teams");
        teamBrowseMeta.setLore(List.of("§7Browse existing teams."));
        TEAM_BROWSE.setItemMeta(teamBrowseMeta);

        ItemMeta teamCreateMeta = TEAM_CREATE.getItemMeta();
        teamCreateMeta.setDisplayName("§eCreate Team");
        teamCreateMeta.setLore(List.of("§7Create a new team on your own."));
        TEAM_CREATE.setItemMeta(teamCreateMeta);

        ItemMeta teamSettingsMeta = TEAM_SETTINGS.getItemMeta();
        teamSettingsMeta.setDisplayName("§eSettings");
        teamSettingsMeta.setLore(List.of("§7Adjust your team's settings."));
        TEAM_SETTINGS.setItemMeta(teamSettingsMeta);

        ItemMeta teamChangeNameMeta = TEAM_CHANGE_NAME.getItemMeta();
        teamChangeNameMeta.setDisplayName("§eChange Name");
        teamChangeNameMeta.setLore(List.of("§7Change your team's public name."));
        TEAM_CHANGE_NAME.setItemMeta(teamChangeNameMeta);

        ItemMeta teamChangeDescriptionMeta = TEAM_CHANGE_DESCRIPTION.getItemMeta();
        teamChangeDescriptionMeta.setDisplayName("§eChange Description");
        teamChangeDescriptionMeta.setLore(List.of("§7Change your team's description."));
        TEAM_CHANGE_DESCRIPTION.setItemMeta(teamChangeDescriptionMeta);

        ItemMeta teamChangeStatusMeta = TEAM_CHANGE_STATUS.getItemMeta();
        teamChangeStatusMeta.setDisplayName("§eChange Status");
        teamChangeStatusMeta.setLore(List.of("§7Change your team's status."));
        TEAM_CHANGE_STATUS.setItemMeta(teamChangeStatusMeta);

        ItemMeta teamChangeStatusClosedMeta = TEAM_CHANGE_STATUS_CLOSED.getItemMeta();
        teamChangeStatusClosedMeta.setDisplayName("§cClosed");
        teamChangeStatusClosedMeta.setLore(List.of("§7Nobody can join the team."));
        TEAM_CHANGE_STATUS_CLOSED.setItemMeta(teamChangeStatusClosedMeta);

        ItemMeta teamChangeStatusInviteMeta = TEAM_CHANGE_STATUS_INVITE.getItemMeta();
        teamChangeStatusInviteMeta.setDisplayName("§dInvite");
        teamChangeStatusInviteMeta.setLore(List.of("§7Players need to request to join."));
        TEAM_CHANGE_STATUS_INVITE.setItemMeta(teamChangeStatusInviteMeta);

        ItemMeta teamChangeStatusOpenMeta = TEAM_CHANGE_STATUS_OPEN.getItemMeta();
        teamChangeStatusOpenMeta.setDisplayName("§aOpen");
        teamChangeStatusOpenMeta.setLore(List.of("§7Everybody can join without asking."));
        TEAM_CHANGE_STATUS_OPEN.setItemMeta(teamChangeStatusOpenMeta);

        ItemMeta teamLeaveMeta = TEAM_LEAVE.getItemMeta();
        teamLeaveMeta.setDisplayName("§cLeave Team");
        teamLeaveMeta.setLore(List.of("§7Leave the team you are in."));
        TEAM_LEAVE.setItemMeta(teamLeaveMeta);

        ItemMeta teamLeaveConfirmMeta = TEAM_LEAVE_CONFIRM.getItemMeta();
        teamLeaveConfirmMeta.setDisplayName("§c§lARE YOU SURE?");
        teamLeaveConfirmMeta.setLore(List.of("§7Are you sure you want to leave?", "§r", "§7This action cannot be undone.", "§7You need to be re-invited into", "§7the team in order to join back."));
        TEAM_LEAVE_CONFIRM.setItemMeta(teamLeaveConfirmMeta);

        ItemMeta teamKickPlayerMeta = TEAM_KICK_PLAYER.getItemMeta();
        teamKickPlayerMeta.setDisplayName("§cKick Player");
        teamKickPlayerMeta.setLore(List.of("§7Kick this player from your team."));
        TEAM_KICK_PLAYER.setItemMeta(teamKickPlayerMeta);

        ItemMeta teamPromotePlayerMeta = TEAM_PROMOTE_PLAYER.getItemMeta();
        teamPromotePlayerMeta.setDisplayName("§ePromote Player");
        teamPromotePlayerMeta.setLore(List.of("§7Set this player as the team owner.", "§r", "§cWarning: §7You will lose your ownership."));
        TEAM_PROMOTE_PLAYER.setItemMeta(teamPromotePlayerMeta);

        ItemMeta resetCharacterMeta = RESET_CHARACTER.getItemMeta();
        resetCharacterMeta.setDisplayName("§cReset Character");
        resetCharacterMeta.setLore(List.of("§7Are you sure you want to reset", "§7all your progress?", "§r", "§cThis cannot be undone§7!", "§r", "§7Click to continue."));
        RESET_CHARACTER.setItemMeta(resetCharacterMeta);

        ItemMeta resetCharacterConfirmMeta = RESET_CHARACTER_CONFIRM.getItemMeta();
        resetCharacterConfirmMeta.setDisplayName("§c§lARE YOU SURE?");
        resetCharacterConfirmMeta.setLore(List.of("§7Are you sure you want to reset", "§7all your progress?", "§r", "§cThis cannot be undone§7!", "§r", "§7Click to continue."));
        RESET_CHARACTER_CONFIRM.setItemMeta(resetCharacterConfirmMeta);

        ItemMeta treeTrackLockedMeta = TREE_TRACK_LOCKED.getItemMeta();
        treeTrackLockedMeta.setDisplayName(" ");
        TREE_TRACK_LOCKED.setItemMeta(treeTrackLockedMeta);

        ItemMeta treeTrackUnlockedMeta = TREE_TRACK_UNLOCKED.getItemMeta();
        treeTrackUnlockedMeta.setDisplayName(" ");
        TREE_TRACK_UNLOCKED.setItemMeta(treeTrackUnlockedMeta);

        ItemMeta treeResetMeta = TREE_RESET.getItemMeta();
        treeResetMeta.setDisplayName("§cReset Loadout");
        treeResetMeta.setLore(List.of(
                "§7Remove all items from your loadout.",
                "§r",
                "§cThis will only clear your loadout,",
                "§cNot delete any progress you made!"
        ));
        TREE_RESET.setItemMeta(treeResetMeta);

        ItemMeta treeHelpMeta = TREE_HELP.getItemMeta();
        treeHelpMeta.setDisplayName("§aHow To Use");
        treeHelpMeta.setLore(List.of(
                "§7First, you need to gain §eⓄ Skill Points",
                "§7by donating diamond blocks to the",
                "§7altar. You can donate normal diamonds",
                "§7to the altar to gain &bXP§7.",
                "§r",
                "§7Then, you can unlock abilities with these",
                "§7points. After you bought them, click them",
                "§7again to equip them.",
                "§r",
                "§7You can only equip up to §b8§7 abilities and",
                "§7cannot carry more than your maximum §5weight§7.",
                "§7The max weight is determined by your level.",
                "§r",
                "§eCheck the wiki for more information."
        ));
        TREE_HELP.setItemMeta(treeHelpMeta);

        ItemMeta characterChangeNameMeta = CHARACTER_CHANGE_NAME.getItemMeta();
        characterChangeNameMeta.setDisplayName("§eChange Name");
        characterChangeNameMeta.setLore(List.of("§7Change your characters's name."));
        CHARACTER_CHANGE_NAME.setItemMeta(characterChangeNameMeta);

    }

}
