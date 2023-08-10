package net.toujoustudios.hyperspecies.data.character;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class Character {

    public static Inventory buildInventory(Player player) {

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        Inventory inventory = Bukkit.createInventory(null, 9 * 5, Component.text("Your Character"));
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemListUI.FILLER);

        ItemStack character = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta characterMeta = (SkullMeta) character.getItemMeta();
        characterMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        characterMeta.displayName(Component.text("§eYour Character"));
        characterMeta.setLore(List.of(
                "§7Species: " + playerManager.getSpecies().prefix(),
                "§7Subspecies: §a" + (playerManager.getSubSpecies() != null ? playerManager.getSubSpecies().name() : "§8None"),
                "§7Level: §b" + playerManager.getLevel(),
                "§7Skill Points: §eⓄ " + playerManager.getSkill()
        ));
        character.setItemMeta(characterMeta);

        inventory.setItem(13, character);
        inventory.setItem(28, ItemListUI.PREVIOUS);

        if(playerManager.getLevel() > 7) {
            if(playerManager.getSubSpecies() != null) {
                ItemStack subSpecies = new ItemStack(playerManager.getSpecies().icon().getType());
                ItemMeta subSpeciesMeta = subSpecies.getItemMeta();
                subSpeciesMeta.displayName(Component.text("§a" + playerManager.getSubSpecies().name()));
                subSpeciesMeta.setLore(List.of("§7You already have a subspecies."));
                subSpecies.setItemMeta(subSpeciesMeta);
                inventory.setItem(34, subSpecies);
            } else {
                ItemStack subSpecies = new ItemStack(playerManager.getSpecies().icon().getType());
                ItemMeta subSpeciesMeta = subSpecies.getItemMeta();
                subSpeciesMeta.displayName(Component.text("§aSelect Subspecies"));
                subSpeciesMeta.setLore(List.of("§7Click to select a subspecies."));
                subSpecies.setItemMeta(subSpeciesMeta);
                inventory.setItem(34, subSpecies);
            }
        } else {
            ItemStack subSpecies = new ItemStack(Material.GRAY_WOOL);
            ItemMeta subSpeciesMeta = subSpecies.getItemMeta();
            subSpeciesMeta.displayName(Component.text("§cNo Subspecies"));
            subSpeciesMeta.setLore(List.of("§7You cannot select a subspecies yet."));
            subSpecies.setItemMeta(subSpeciesMeta);
            inventory.setItem(34, subSpecies);
        }

        return inventory;

    }

}
