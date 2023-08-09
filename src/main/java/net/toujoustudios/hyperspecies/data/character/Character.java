package net.toujoustudios.hyperspecies.data.character;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class Character {

    public static Inventory openInventory(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        Inventory inventory = Bukkit.createInventory(null, 9 * 3, Component.text("Your Character"));
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemListUI.FILLER);

        ItemStack character = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta characterMeta = (SkullMeta) character.getItemMeta();
        characterMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        characterMeta.displayName(Component.text("§eYour Character"));
        characterMeta.setLore(List.of(
                "§7Species: " + playerManager.getSpecies().prefix(),
                "§7Subspecies: " + (playerManager.getSubSpecies() != null ? playerManager.getSubSpecies() : "§8None"),
                "§7Level: §b" + playerManager.getLevel(),
                "§7Skill Points: §eⓄ " + playerManager.getSkill()
        ));
        character.setItemMeta(characterMeta);

        inventory.setItem(10, character);

        return inventory;

    }

}
