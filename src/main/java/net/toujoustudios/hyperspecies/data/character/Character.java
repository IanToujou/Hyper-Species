package net.toujoustudios.hyperspecies.data.character;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Character {

    public static Inventory openInventory(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        Inventory inventory = Bukkit.createInventory(null, 9 * 6, Component.text("Your Character"));
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemListUI.FILLER);

        return inventory;

    }

}
