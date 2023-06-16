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

        if(event.getView().getTitle().equals("Ability Trees")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if(event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();

            if(material == Material.NETHER_STAR) {

                String treeName = name.substring(2);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

                AbilityTree tree = AbilityTree.getTree(treeName);

                if(tree != null) {

                    player.openInventory(tree.buildInventory());

                }

            }

        } else if(event.getView().getTitle().startsWith("Tree: ")) {

            if(event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();

            if(material == Material.BARRIER) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.openInventory(AbilityTree.buildMainInventory());

            }

        }

    }

}
