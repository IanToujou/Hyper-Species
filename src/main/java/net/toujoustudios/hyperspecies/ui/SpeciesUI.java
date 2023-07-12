package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class SpeciesUI implements Listener {

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
                openInventory(player, SpeciesPage.DEMON.getIndex());
            } else if(material == Material.SWEET_BERRIES) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.ELF.getIndex());
            } else if(material == Material.NAUTILUS_SHELL) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.REPTILE.getIndex());
            } else if(material == Material.TOTEM_OF_UNDYING) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.ANGEL.getIndex());
            } else if(material == Material.MINECART) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.HUMAN.getIndex());
            } else if(material == Material.HEART_OF_THE_SEA) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.AQUATILIA.getIndex());
            } else if(material == Material.PHANTOM_MEMBRANE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.FELINE.getIndex());
            } else if(material == Material.RAW_GOLD) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.DWARF.getIndex());
            } else if(material == Material.BONE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, SpeciesPage.WOLF.getIndex());
            }

        } else if(event.getView().getTitle().contains("Species: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            String className = event.getView().getTitle().split(" ")[1];

            if(material == Material.PLAYER_HEAD) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player);

            } else if(material == Material.GREEN_CONCRETE) {

                PlayerManager playerManager = PlayerManager.getPlayer(player);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_PREFIX + " §7You selected the species §b" + className + "§8.");
                playerManager.setSpecies(Species.getSpecies(className));
                playerManager.refreshScoreboard();

            }

        }

    }

    public static void initialize() {

        // MAIN

        Inventory pageMain = Bukkit.createInventory(null, 9*5, "Species Selection");
        for(int i = 0; i < pageMain.getSize(); i++) pageMain.setItem(i, ItemListUI.FILLER);
        pageMain.setItem(10, ItemListUI.DEMON);
        pageMain.setItem(12, ItemListUI.ELF);
        pageMain.setItem(14, ItemListUI.REPTILE);
        pageMain.setItem(16, ItemListUI.ANGEL);
        pageMain.setItem(19, ItemListUI.HUMAN);
        pageMain.setItem(21, ItemListUI.AQUATILIA);
        pageMain.setItem(23, ItemListUI.FELINE);
        pageMain.setItem(25, ItemListUI.DWARF);
        pageMain.setItem(28, ItemListUI.WOLF);
        inventories.put(SpeciesPage.MAIN.getIndex(), pageMain);

        // DEMON

        Inventory pageDemon = Bukkit.createInventory(null, 9*3, "Species: Demon");
        for(int i = 0; i < pageDemon.getSize(); i++) pageDemon.setItem(i, ItemListUI.FILLER);
        pageDemon.setItem(10, ItemListUI.PREVIOUS);
        pageDemon.setItem(13, ItemListUI.DEMON_PASSIVE_ABILITIES);
        pageDemon.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.DEMON.getIndex(), pageDemon);

        // ELF

        Inventory pageElf = Bukkit.createInventory(null, 9*3, "Species: Elf");
        for(int i = 0; i < pageElf.getSize(); i++) pageElf.setItem(i, ItemListUI.FILLER);
        pageElf.setItem(10, ItemListUI.PREVIOUS);
        pageElf.setItem(13, ItemListUI.ELF_PASSIVE_ABILITIES);
        pageElf.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.ELF.getIndex(), pageElf);

        // REPTILE

        Inventory pageReptile = Bukkit.createInventory(null, 9*3, "Species: Reptile");
        for(int i = 0; i < pageReptile.getSize(); i++) pageReptile.setItem(i, ItemListUI.FILLER);
        pageReptile.setItem(10, ItemListUI.PREVIOUS);
        pageReptile.setItem(13, ItemListUI.REPTILE_PASSIVE_ABILITIES);
        pageReptile.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.REPTILE.getIndex(), pageReptile);

        // ANGEL

        Inventory pageAngel = Bukkit.createInventory(null, 9*3, "Species: Angel");
        for(int i = 0; i < pageAngel.getSize(); i++) pageAngel.setItem(i, ItemListUI.FILLER);
        pageAngel.setItem(10, ItemListUI.PREVIOUS);
        pageAngel.setItem(13, ItemListUI.ANGEL_PASSIVE_ABILITIES);
        pageAngel.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.ANGEL.getIndex(), pageAngel);

        // HUMAN

        Inventory pageHuman = Bukkit.createInventory(null, 9*3, "Species: Human");
        for(int i = 0; i < pageHuman.getSize(); i++) pageHuman.setItem(i, ItemListUI.FILLER);
        pageHuman.setItem(10, ItemListUI.PREVIOUS);
        pageHuman.setItem(13, ItemListUI.HUMAN_PASSIVE_ABILITIES);
        pageHuman.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.HUMAN.getIndex(), pageHuman);

        // AQUATILIA

        Inventory pageAquatilia = Bukkit.createInventory(null, 9*3, "Species: Aquatilia");
        for(int i = 0; i < pageAquatilia.getSize(); i++) pageAquatilia.setItem(i, ItemListUI.FILLER);
        pageAquatilia.setItem(10, ItemListUI.PREVIOUS);
        pageAquatilia.setItem(13, ItemListUI.AQUATILIA_PASSIVE_ABILITIES);
        pageAquatilia.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.AQUATILIA.getIndex(), pageAquatilia);

        // FELINE

        Inventory pageFeline = Bukkit.createInventory(null, 9*3, "Species: Feline");
        for(int i = 0; i < pageFeline.getSize(); i++) pageFeline.setItem(i, ItemListUI.FILLER);
        pageFeline.setItem(10, ItemListUI.PREVIOUS);
        pageFeline.setItem(13, ItemListUI.FELINE_PASSIVE_ABILITIES);
        pageFeline.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.FELINE.getIndex(), pageFeline);

        // DWARF

        Inventory pageDwarf = Bukkit.createInventory(null, 9*3, "Species: Dwarf");
        for(int i = 0; i < pageDwarf.getSize(); i++) pageDwarf.setItem(i, ItemListUI.FILLER);
        pageDwarf.setItem(10, ItemListUI.PREVIOUS);
        pageDwarf.setItem(13, ItemListUI.DWARF_PASSIVE_ABILITIES);
        pageDwarf.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.DWARF.getIndex(), pageDwarf);

        // WOLF

        Inventory pageWolf = Bukkit.createInventory(null, 9*3, "Species: Wolf");
        for(int i = 0; i < pageWolf.getSize(); i++) pageWolf.setItem(i, ItemListUI.FILLER);
        pageWolf.setItem(10, ItemListUI.PREVIOUS);
        pageWolf.setItem(13, ItemListUI.WOLF_PASSIVE_ABILITIES);
        pageWolf.setItem(16, ItemListUI.CONFIRM);
        inventories.put(SpeciesPage.WOLF.getIndex(), pageWolf);

    }

    public static void openInventory(Player player) {
        openInventory(player, 0);
    }

    public static void openInventory(Player player, int page) {
        if(inventories.get(page) == null) return;
        player.openInventory(inventories.get(page));
    }

}
