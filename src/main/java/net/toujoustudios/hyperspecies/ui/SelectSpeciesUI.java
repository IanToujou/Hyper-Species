package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.item.ItemList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class SelectSpeciesUI implements Listener {

    private static final ArrayList<Inventory> inventories = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equals("Species Selection")) {

            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if(material == Material.FIRE_CHARGE) openInventory(player, SelectSpeciesPage.DEMON.getIndex());
            if(material == Material.SWEET_BERRIES) openInventory(player, SelectSpeciesPage.ELF.getIndex());
            if(material == Material.TOTEM_OF_UNDYING) openInventory(player, SelectSpeciesPage.ANGEL.getIndex());

        } else if(event.getView().getTitle().contains("Species: ")) {

            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            String className = event.getView().getTitle().split(" ")[1];
            if(material == Material.RED_CONCRETE) openInventory(player);
            if(material == Material.GREEN_CONCRETE) player.closeInventory();
            if(material == Material.ENDER_EYE) openInventory(player, SelectSpeciesPage.valueOf(className.toUpperCase()).getIndex());

        } else if(event.getView().getTitle().contains("Abilities: ")) {

            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
            event.setCancelled(true);
            
            Material material = event.getCurrentItem().getType();
            String className = event.getView().getTitle().split(" ")[1];
            if(material == Material.PLAYER_HEAD) openInventory(player, SelectSpeciesPage.valueOf(className.toUpperCase()).getIndex());

        }

    }

    public static void initialize() {

        Inventory pageMain = Bukkit.createInventory(null, 9*6, "Species Selection");
        for(int i = 0; i < pageMain.getSize(); i++) pageMain.setItem(i, ItemList.FILLER);
        pageMain.setItem(pageMain.getSize()-1, ItemList.NEXT_PAGE);
        pageMain.setItem(10, ItemList.DEMON);
        pageMain.setItem(12, ItemList.ELF);
        pageMain.setItem(14, ItemList.REPTILE);
        pageMain.setItem(16, ItemList.ANGEL);
        pageMain.setItem(19, ItemList.HUMAN);
        pageMain.setItem(21, ItemList.SEA_CREATURE);
        pageMain.setItem(23, ItemList.FELINE);
        pageMain.setItem(25, ItemList.DWARF);
        pageMain.setItem(28, ItemList.WOLF);
        inventories.add(SelectSpeciesPage.MAIN.getIndex(), pageMain);

        Inventory pageDemon = Bukkit.createInventory(null, 9*3, "Species: Demon");
        for(int i = 0; i < pageDemon.getSize(); i++) pageDemon.setItem(i, ItemList.FILLER);
        pageDemon.setItem(10, ItemList.DEMON);
        pageDemon.setItem(12, ItemList.DEMON_PASSIVE_ABILITIES);
        pageDemon.setItem(13, ItemList.DEMON_ACTIVE_ABILITIES);
        pageDemon.setItem(15, ItemList.CONFIRM);
        pageDemon.setItem(16, ItemList.CANCEL);
        inventories.add(SelectSpeciesPage.DEMON.getIndex(), pageDemon);

        Inventory pageDemonAbilities = Bukkit.createInventory(null, 9*3, "Abilities: Demon");
        for(int i = 0; i < pageDemonAbilities.getSize(); i++) pageDemonAbilities.setItem(i, ItemList.FILLER);
        pageDemonAbilities.setItem(10, ItemList.PREVIOUS_PAGE);
        inventories.add(SelectSpeciesPage.DEMON_ABILITIES.getIndex(), pageDemonAbilities);

        Inventory pageAngel = Bukkit.createInventory(null, 9*3, "Species: Angel");
        for(int i = 0; i < pageAngel.getSize(); i++) pageAngel.setItem(i, ItemList.FILLER);
        pageAngel.setItem(10, ItemList.ANGEL);
        pageAngel.setItem(12, ItemList.ANGEL_PASSIVE_ABILITIES);
        pageAngel.setItem(13, ItemList.ANGEL_ACTIVE_ABILITIES);
        pageAngel.setItem(15, ItemList.CONFIRM);
        pageAngel.setItem(16, ItemList.CANCEL);
        inventories.add(SelectSpeciesPage.ANGEL.getIndex(), pageAngel);

        Inventory pageAngelAbilities = Bukkit.createInventory(null, 9*3, "Abilities: Angel");
        for(int i = 0; i < pageAngelAbilities.getSize(); i++) pageAngelAbilities.setItem(i, ItemList.FILLER);
        pageAngelAbilities.setItem(10, ItemList.PREVIOUS_PAGE);
        inventories.add(SelectSpeciesPage.ANGEL_ABILITIES.getIndex(), pageAngelAbilities);

    }

    public static void openInventory(Player player) {
        openInventory(player, 0);
    }

    public static void openInventory(Player player, int page) {
        if(inventories.get(page) == null) return;
        player.openInventory(inventories.get(page));
    }

}
