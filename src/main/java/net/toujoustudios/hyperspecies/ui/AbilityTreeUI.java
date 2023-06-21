package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.data.ability.tree.AbilityTree;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AbilityTreeUI implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().startsWith("Ability Trees")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if(event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            int page = Integer.parseInt(event.getView().getTitle().split(" ")[3]);

            if(material == Material.NETHER_STAR) {

                String treeName = name.split(" ", 2)[1];
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

                AbilityTree tree = AbilityTree.getTree(treeName);

                if(tree != null) {
                    player.openInventory(tree.buildInventory(player));
                }

            } else if(material == Material.BARRIER) {

                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

            } else if(material == Material.PLAYER_HEAD) {

                if(name.contains("Back")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                    player.openInventory(AbilityTree.buildMainInventory(player, page-2));
                } else if(name.contains("Next")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                    player.openInventory(AbilityTree.buildMainInventory(player, page));
                }

            }

        } else if(event.getView().getTitle().startsWith("Tree: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.BARRIER) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.openInventory(AbilityTree.buildMainInventory(player, 0));

            }

        }

    }

}
