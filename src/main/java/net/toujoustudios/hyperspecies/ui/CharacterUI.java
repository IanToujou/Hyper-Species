package net.toujoustudios.hyperspecies.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CharacterUI implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Your Character")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if (event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();


        }

    }

}
