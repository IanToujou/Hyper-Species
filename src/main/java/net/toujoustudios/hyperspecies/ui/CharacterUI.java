package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.character.Character;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.species.SubSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterUI implements Listener {

    private static final ArrayList<UUID> changingNamePlayers = new ArrayList<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals("Your Character")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta() == null) return;
            Material material = event.getCurrentItem().getType();
            String name = event.getCurrentItem().getItemMeta().getDisplayName();

            if (material == Material.NAME_TAG) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_PREFIX + " §7Please type in a new team name§8. §7Type §bcancel§7 to cancel§8.");
                changingNamePlayers.add(player.getUniqueId());

            } else if (name.equals("§aSelect Subspecies")) {
                PlayerManager playerManager = PlayerManager.get(player);
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
            } else if (name.equals("§eBack")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.closeInventory();
            }

        } else if (event.getView().getTitle().equals("Select Subspecies")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);
            if (event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();

            if (name.equals("§eBack")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.openInventory(Character.buildInventory(player));
            } else if (name.startsWith("§a")) {
                PlayerManager playerManager = PlayerManager.get(player);
                SubSpecies sub = playerManager.getSpecies().getSubSpecies(name.substring(2));
                if (sub == null) return;
                playerManager.setSubSpecies(sub);
                player.closeInventory();
                player.sendMessage(Config.MESSAGE_PREFIX + "§7 You selected the subspecies§8: §b" + sub.name());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 2f);
            }

        }

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (getChangingNamePlayers().contains(player.getUniqueId())) {

            event.setCancelled(true);
            String name = event.getMessage();
            Pattern pattern = Pattern.compile("^[ A-Za-z]+$");
            Matcher matcher = pattern.matcher(name);

            if (name.equalsIgnoreCase("cancel")) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cCharacter name change has been cancelled§8.");
                changingNamePlayers.remove(player.getUniqueId());
                return;
            }
            if (!matcher.matches()) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe character name can only contain letters and spaces§8.");
                return;
            }
            if (name.length() > 30) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.5f);
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe name cannot be longer than 30 characters§8.");
                return;
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);
            player.sendMessage(Config.MESSAGE_PREFIX + " §7Your character got renamed to §b" + name + "§8.");
            PlayerManager playerManager = PlayerManager.get(player);
            playerManager.name(name);
            playerManager.refreshScoreboard();
            changingNamePlayers.remove(player.getUniqueId());

        }

    }

    public static ArrayList<UUID> getChangingNamePlayers() {
        return changingNamePlayers;
    }

}
