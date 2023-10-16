package net.toujoustudios.hyperspecies.character;

import net.toujoustudios.hyperspecies.item.ItemListUI;
import net.toujoustudios.hyperspecies.player.PlayerManager;
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

        PlayerManager playerManager = PlayerManager.get(player);

        Inventory inventory = Bukkit.createInventory(null, 9 * 5, "Your Character");
        for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, ItemListUI.FILLER);

        String characterName = (playerManager.name() != null ? playerManager.name() : player.getName());

        ItemStack character = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta characterMeta = (SkullMeta) character.getItemMeta();
        assert characterMeta != null;
        characterMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        characterMeta.setDisplayName("§e" + characterName + " §7(§bLVL " + playerManager.getLevel() + "§7)");
        characterMeta.setLore(List.of(
                "§7Character Details:",
                "",
                "§7Species: " + playerManager.getSpecies().prefix(),
                "§7Subspecies: §a" + (playerManager.getSubSpecies() != null ? playerManager.getSubSpecies().name() : "§8None"),
                "§7Skill Points: §eⓄ " + playerManager.getSkill()
        ));
        character.setItemMeta(characterMeta);

        inventory.setItem(13, character);
        inventory.setItem(28, ItemListUI.PREVIOUS);
        inventory.setItem(31, ItemListUI.CHARACTER_CHANGE_NAME);

        if (playerManager.getLevel() > 7) {
            if (playerManager.getSubSpecies() != null) {
                ItemStack subSpecies = new ItemStack(playerManager.getSpecies().icon().getType());
                ItemMeta subSpeciesMeta = subSpecies.getItemMeta();
                assert subSpeciesMeta != null;
                subSpeciesMeta.setDisplayName("§a" + playerManager.getSubSpecies().name());
                subSpeciesMeta.setLore(List.of("§7You already have a subspecies."));
                subSpecies.setItemMeta(subSpeciesMeta);
                inventory.setItem(34, subSpecies);
            } else {
                ItemStack subSpecies = new ItemStack(playerManager.getSpecies().icon().getType());
                ItemMeta subSpeciesMeta = subSpecies.getItemMeta();
                assert subSpeciesMeta != null;
                subSpeciesMeta.setDisplayName("§aSelect Subspecies");
                subSpeciesMeta.setLore(List.of("§7Click to select a subspecies."));
                subSpecies.setItemMeta(subSpeciesMeta);
                inventory.setItem(34, subSpecies);
            }
        } else {
            ItemStack subSpecies = new ItemStack(Material.GRAY_WOOL);
            ItemMeta subSpeciesMeta = subSpecies.getItemMeta();
            assert subSpeciesMeta != null;
            subSpeciesMeta.setDisplayName("§cNo Subspecies");
            subSpeciesMeta.setLore(List.of("§7You cannot select a subspecies yet."));
            subSpecies.setItemMeta(subSpeciesMeta);
            inventory.setItem(34, subSpecies);
        }

        return inventory;

    }

}
