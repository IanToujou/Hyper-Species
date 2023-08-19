package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class ResetUI implements Listener {

    private static final YamlConfiguration playerConfig = Config.getConfigFile("players.yml");

    private static final HashMap<Integer, Inventory> inventories = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Reset Character")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if (material == Material.RED_CONCRETE) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player, ResetPage.CONFIRM.getIndex());
            }

        } else if (event.getView().getTitle().equals("Reset Character: Confirm")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if (material == Material.REDSTONE_BLOCK) {

                PlayerManager playerManager = PlayerManager.get(player);
                playerConfig.set("Data." + player.getUniqueId(), null);
                Config.saveToFile(playerConfig, "players.yml");
                playerManager.destroy();
                playerConfig.set("Data." + player.getUniqueId(), null);
                Config.saveToFile(playerConfig, "players.yml");
                player.kickPlayer("§c§lYour character has been reset.\n\n§7Please wait a few seconds before joining again.");

                Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
                    playerConfig.set("Data." + player.getUniqueId(), null);
                    Config.saveToFile(playerConfig, "players.yml");
                    playerManager.destroy();
                }, 20);

            } else if (material == Material.PLAYER_HEAD) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                openInventory(player);

            }

        }

    }

    public static void initialize() {

        // MAIN
        Inventory pageMain = Bukkit.createInventory(null, 9 * 3, "Reset Character");
        for (int i = 0; i < pageMain.getSize(); i++) pageMain.setItem(i, ItemListUI.FILLER);
        pageMain.setItem(13, ItemListUI.RESET_CHARACTER);

        // CONFIRM
        Inventory pageConfirm = Bukkit.createInventory(null, 9 * 3, "Reset Character: Confirm");
        for (int i = 0; i < pageConfirm.getSize(); i++) pageConfirm.setItem(i, ItemListUI.FILLER);
        pageConfirm.setItem(11, ItemListUI.PREVIOUS);
        pageConfirm.setItem(15, ItemListUI.RESET_CHARACTER_CONFIRM);

        inventories.put(ResetPage.MAIN.getIndex(), pageMain);
        inventories.put(ResetPage.CONFIRM.getIndex(), pageConfirm);


    }

    public static void openInventory(Player player) {
        openInventory(player, 0);
    }

    public static void openInventory(Player player, int page) {
        if (inventories.get(page) == null) return;
        player.openInventory(inventories.get(page));
    }

}
