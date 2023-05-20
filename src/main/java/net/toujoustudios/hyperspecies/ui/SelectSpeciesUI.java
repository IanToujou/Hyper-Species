package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class SelectSpeciesUI implements Listener {

    private static final HashMap<Integer, Inventory> inventories = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equals("Species Selection")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if(material == Material.FIRE_CHARGE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.DEMON.getIndex());
            } else if(material == Material.SWEET_BERRIES) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.ELF.getIndex());
            } else if(material == Material.NAUTILUS_SHELL) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.REPTILE.getIndex());
            } else if(material == Material.TOTEM_OF_UNDYING) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.ANGEL.getIndex());
            } else if(material == Material.MINECART) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.HUMAN.getIndex());
            } else if(material == Material.HEART_OF_THE_SEA) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.AQUATILIA.getIndex());
            } else if(material == Material.PHANTOM_MEMBRANE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.FELINE.getIndex());
            } else if(material == Material.RAW_GOLD) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.DWARF.getIndex());
            } else if(material == Material.BONE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.WOLF.getIndex());
            }

        } else if(event.getView().getTitle().contains("Species: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            String className = event.getView().getTitle().split(" ")[1];

            if(material == Material.RED_CONCRETE) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player);

            } else if(material == Material.GREEN_CONCRETE) {

                PlayerManager playerManager = PlayerManager.getPlayer(player);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_INFO_SPECIES_SELECT.replace("{Species}", className));
                playerManager.setSpecies(Species.getSpecies(className));

            } else if(material == Material.ENDER_EYE) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.valueOf(className.toUpperCase() + "_ABILITIES").getIndex());

            }

        } else if(event.getView().getTitle().contains("Abilities: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);
            
            Material material = event.getCurrentItem().getType();
            String className = event.getView().getTitle().split(" ")[1];

            if(material == Material.PLAYER_HEAD) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SelectSpeciesPage.valueOf(className.toUpperCase()).getIndex());
            }

        }

    }

    public static void initialize() {

        // MAIN

        Inventory pageMain = Bukkit.createInventory(null, 9*5, "Species Selection");
        for(int i = 0; i < pageMain.getSize(); i++) pageMain.setItem(i, ItemList.FILLER);
        pageMain.setItem(10, ItemList.DEMON);
        pageMain.setItem(12, ItemList.ELF);
        pageMain.setItem(14, ItemList.REPTILE);
        pageMain.setItem(16, ItemList.ANGEL);
        pageMain.setItem(19, ItemList.HUMAN);
        pageMain.setItem(21, ItemList.AQUATILIA);
        pageMain.setItem(23, ItemList.FELINE);
        pageMain.setItem(25, ItemList.DWARF);
        pageMain.setItem(28, ItemList.WOLF);
        inventories.put(SelectSpeciesPage.MAIN.getIndex(), pageMain);

        // DEMON

        Inventory pageDemon = Bukkit.createInventory(null, 9*3, "Species: Demon");
        for(int i = 0; i < pageDemon.getSize(); i++) pageDemon.setItem(i, ItemList.FILLER);
        pageDemon.setItem(10, ItemList.DEMON);
        pageDemon.setItem(12, ItemList.DEMON_PASSIVE_ABILITIES);
        pageDemon.setItem(13, ItemList.DEMON_ACTIVE_ABILITIES);
        pageDemon.setItem(15, ItemList.CONFIRM);
        pageDemon.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.DEMON.getIndex(), pageDemon);

        Inventory pageDemonAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Demon");
        for(int i = 0; i < pageDemonAbilities.getSize(); i++) pageDemonAbilities.setItem(i, ItemList.FILLER);
        pageDemonAbilities.setItem(21, ItemList.DEMON_ABILITY_ENHANCING_FLAME);
        pageDemonAbilities.setItem(22, ItemList.DEMON_ABILITY_HELLBLIGHT);
        pageDemonAbilities.setItem(23, ItemList.DEMON_ABILITY_DEMONIC_RAGE);
        pageDemonAbilities.setItem(24, ItemList.DEMON_ABILITY_METEOR_STRIKE);
        pageDemonAbilities.setItem(15, ItemList.DEMON_ABILITY_RAGING_BURST);
        pageDemonAbilities.setItem(16, ItemList.DEMON_ABILITY_MAGMATIC_DETONATION);
        pageDemonAbilities.setItem(33, ItemList.DEMON_ABILITY_FOCUS);
        pageDemonAbilities.setItem(34, ItemList.DEMON_ABILITY_STRIKE_OF_CORRUPTION);
        pageDemonAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.DEMON_ABILITIES.getIndex(), pageDemonAbilities);

        // ELF

        Inventory pageElf = Bukkit.createInventory(null, 9*3, "Species: Elf");
        for(int i = 0; i < pageElf.getSize(); i++) pageElf.setItem(i, ItemList.FILLER);
        pageElf.setItem(10, ItemList.ELF);
        pageElf.setItem(12, ItemList.ELF_PASSIVE_ABILITIES);
        pageElf.setItem(13, ItemList.ELF_ACTIVE_ABILITIES);
        pageElf.setItem(15, ItemList.CONFIRM);
        pageElf.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.ELF.getIndex(), pageElf);

        Inventory pageElfAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Elf");
        for(int i = 0; i < pageElfAbilities.getSize(); i++) pageElfAbilities.setItem(i, ItemList.FILLER);
        pageElfAbilities.setItem(21, ItemList.ELF_ABILITY_HEALING_NATURE);
        pageElfAbilities.setItem(22, ItemList.ELF_ABILITY_THORN_SEEDLING);
        pageElfAbilities.setItem(23, ItemList.ELF_ABILITY_RAGING_FLORA);
        pageElfAbilities.setItem(24, ItemList.ELF_ABILITY_GREEN_HELL);
        pageElfAbilities.setItem(15, ItemList.ELF_ABILITY_WOODLANDS_BLESSING);
        pageElfAbilities.setItem(16, ItemList.ELF_ABILITY_ROOTS_OF_NATURE);
        pageElfAbilities.setItem(33, ItemList.ELF_ABILITY_MANA_POOL);
        pageElfAbilities.setItem(34, ItemList.ELF_ABILITY_PROTECT_THE_HOMELAND);
        pageElfAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.ELF_ABILITIES.getIndex(), pageElfAbilities);

        // REPTILE

        Inventory pageReptile = Bukkit.createInventory(null, 9*3, "Species: Reptile");
        for(int i = 0; i < pageReptile.getSize(); i++) pageReptile.setItem(i, ItemList.FILLER);
        pageReptile.setItem(10, ItemList.REPTILE);
        pageReptile.setItem(12, ItemList.REPTILE_PASSIVE_ABILITIES);
        pageReptile.setItem(13, ItemList.REPTILE_ACTIVE_ABILITIES);
        pageReptile.setItem(15, ItemList.CONFIRM);
        pageReptile.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.REPTILE.getIndex(), pageReptile);

        Inventory pageReptileAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Reptile");
        for(int i = 0; i < pageReptileAbilities.getSize(); i++) pageReptileAbilities.setItem(i, ItemList.FILLER);
        pageReptileAbilities.setItem(40, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.REPTILE_ABILITIES.getIndex(), pageReptileAbilities);

        // ANGEL

        Inventory pageAngel = Bukkit.createInventory(null, 9*3, "Species: Angel");
        for(int i = 0; i < pageAngel.getSize(); i++) pageAngel.setItem(i, ItemList.FILLER);
        pageAngel.setItem(10, ItemList.ANGEL);
        pageAngel.setItem(12, ItemList.ANGEL_PASSIVE_ABILITIES);
        pageAngel.setItem(13, ItemList.ANGEL_ACTIVE_ABILITIES);
        pageAngel.setItem(15, ItemList.CONFIRM);
        pageAngel.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.ANGEL.getIndex(), pageAngel);

        Inventory pageAngelAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Angel");
        for(int i = 0; i < pageAngelAbilities.getSize(); i++) pageAngelAbilities.setItem(i, ItemList.FILLER);
        pageAngelAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.ANGEL_ABILITIES.getIndex(), pageAngelAbilities);

        // HUMAN

        Inventory pageHuman = Bukkit.createInventory(null, 9*3, "Species: Human");
        for(int i = 0; i < pageHuman.getSize(); i++) pageHuman.setItem(i, ItemList.FILLER);
        pageHuman.setItem(10, ItemList.HUMAN);
        pageHuman.setItem(12, ItemList.HUMAN_PASSIVE_ABILITIES);
        pageHuman.setItem(13, ItemList.HUMAN_ACTIVE_ABILITIES);
        pageHuman.setItem(15, ItemList.CONFIRM);
        pageHuman.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.HUMAN.getIndex(), pageHuman);

        Inventory pageHumanAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Human");
        for(int i = 0; i < pageHumanAbilities.getSize(); i++) pageHumanAbilities.setItem(i, ItemList.FILLER);
        pageHumanAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.HUMAN_ABILITIES.getIndex(), pageHumanAbilities);

        // AQUATILIA

        Inventory pageAquatilia = Bukkit.createInventory(null, 9*3, "Species: Aquatilia");
        for(int i = 0; i < pageAquatilia.getSize(); i++) pageAquatilia.setItem(i, ItemList.FILLER);
        pageAquatilia.setItem(10, ItemList.AQUATILIA);
        pageAquatilia.setItem(12, ItemList.AQUATILIA_PASSIVE_ABILITIES);
        pageAquatilia.setItem(13, ItemList.AQUATILIA_ACTIVE_ABILITIES);
        pageAquatilia.setItem(15, ItemList.CONFIRM);
        pageAquatilia.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.AQUATILIA.getIndex(), pageAquatilia);

        Inventory pageAquatiliaAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Aquatilia");
        for(int i = 0; i < pageAquatiliaAbilities.getSize(); i++) pageAquatiliaAbilities.setItem(i, ItemList.FILLER);
        pageAquatiliaAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.AQUATILIA_ABILITIES.getIndex(), pageAquatiliaAbilities);

        // FELINE

        Inventory pageFeline = Bukkit.createInventory(null, 9*3, "Species: Feline");
        for(int i = 0; i < pageFeline.getSize(); i++) pageFeline.setItem(i, ItemList.FILLER);
        pageFeline.setItem(10, ItemList.FELINE);
        pageFeline.setItem(12, ItemList.FELINE_PASSIVE_ABILITIES);
        pageFeline.setItem(13, ItemList.FELINE_ACTIVE_ABILITIES);
        pageFeline.setItem(15, ItemList.CONFIRM);
        pageFeline.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.FELINE.getIndex(), pageFeline);

        Inventory pageFelineAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Feline");
        for(int i = 0; i < pageFelineAbilities.getSize(); i++) pageFelineAbilities.setItem(i, ItemList.FILLER);
        pageFelineAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.FELINE_ABILITIES.getIndex(), pageFelineAbilities);

        // DWARF

        Inventory pageDwarf = Bukkit.createInventory(null, 9*3, "Species: Dwarf");
        for(int i = 0; i < pageDwarf.getSize(); i++) pageDwarf.setItem(i, ItemList.FILLER);
        pageDwarf.setItem(10, ItemList.DWARF);
        pageDwarf.setItem(12, ItemList.DWARF_PASSIVE_ABILITIES);
        pageDwarf.setItem(13, ItemList.DWARF_ACTIVE_ABILITIES);
        pageDwarf.setItem(15, ItemList.CONFIRM);
        pageDwarf.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.DWARF.getIndex(), pageDwarf);

        Inventory pageDwarfAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Dwarf");
        for(int i = 0; i < pageDwarfAbilities.getSize(); i++) pageDwarfAbilities.setItem(i, ItemList.FILLER);
        pageDwarfAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.DWARF_ABILITIES.getIndex(), pageDwarfAbilities);

        // WOLF

        Inventory pageWolf = Bukkit.createInventory(null, 9*3, "Species: Wolf");
        for(int i = 0; i < pageWolf.getSize(); i++) pageWolf.setItem(i, ItemList.FILLER);
        pageWolf.setItem(10, ItemList.WOLF);
        pageWolf.setItem(12, ItemList.WOLF_PASSIVE_ABILITIES);
        pageWolf.setItem(13, ItemList.WOLF_ACTIVE_ABILITIES);
        pageWolf.setItem(15, ItemList.CONFIRM);
        pageWolf.setItem(16, ItemList.CANCEL);
        inventories.put(SelectSpeciesPage.WOLF.getIndex(), pageWolf);

        Inventory pageWolfAbilities = Bukkit.createInventory(null, 9*5, "Abilities: Wolf");
        for(int i = 0; i < pageWolfAbilities.getSize(); i++) pageWolfAbilities.setItem(i, ItemList.FILLER);
        pageWolfAbilities.setItem(19, ItemList.PREVIOUS_PAGE);
        inventories.put(SelectSpeciesPage.WOLF_ABILITIES.getIndex(), pageWolfAbilities);

    }

    public static void openInventory(Player player) {
        openInventory(player, 0);
    }

    public static void openInventory(Player player, int page) {
        if(inventories.get(page) == null) return;
        player.openInventory(inventories.get(page));
    }

}
