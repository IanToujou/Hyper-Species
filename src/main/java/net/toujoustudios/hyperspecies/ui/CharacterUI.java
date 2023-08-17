package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.character.Character;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.SubSpecies;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CharacterUI implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Your Character")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();

            if(name.equals("§aSelect Subspecies")) {
                PlayerManager playerManager = PlayerManager.getPlayer(player);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                Inventory inventory = Bukkit.createInventory(null, 9 * 3, "Select Subspecies");
                for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemListUI.FILLER);
                inventory.setItem(10, ItemListUI.PREVIOUS);
                ItemStack subOne = new ItemStack(playerManager.getSpecies().icon().getType());
                ItemMeta subOneMeta = subOne.getItemMeta();
                assert subOneMeta != null;
                subOneMeta.setDisplayName("§a" + playerManager.getSpecies().subSpecies().get(0).name());
                subOne.setItemMeta(subOneMeta);
                ItemStack subTwo = new ItemStack(playerManager.getSpecies().icon().getType());
                ItemMeta subTwoMeta = subTwo.getItemMeta();
                assert subTwoMeta != null;
                subTwoMeta.setDisplayName("§a" + playerManager.getSpecies().subSpecies().get(1).name());
                subTwo.setItemMeta(subTwoMeta);
                inventory.setItem(13, subOne);
                inventory.setItem(16, subTwo);
                player.openInventory(inventory);
            } else if(name.equals("§eBack")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
            }

        } else if(event.getView().getTitle().equals("Select Subspecies")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();

            if(name.equals("§eBack")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.openInventory(Character.buildInventory(player));
            } else if(name.startsWith("§a")) {
                PlayerManager playerManager = PlayerManager.getPlayer(player);
                SubSpecies sub = playerManager.getSpecies().getSubSpecies(name.substring(2));
                if(sub == null) return;
                playerManager.setSubSpecies(sub);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_PREFIX + "§7 You selected the subspecies§8: §b" + sub.name());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 2f);
            }

        }

    }

}
