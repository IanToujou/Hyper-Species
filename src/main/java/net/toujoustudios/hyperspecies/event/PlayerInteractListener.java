package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final ArrayList<UUID> cooldownPlayers = new ArrayList<>();

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        Action action = event.getAction();

        if(playerManager.isSelectingAbility()) {

            event.setCancelled(true);
            if(cooldownPlayers.contains(player.getUniqueId())) return;
            if(player.getItemInHand().getItemMeta() != null) {
                if(player.getItemInHand().getItemMeta().getDisplayName().contains("§cCancel")) {
                    for(int i = 0; i < 9; i++) player.getInventory().setItem(i, playerManager.getSavedInventory().get(i));
                    playerManager.setSelectingAbility(false);
                    player.sendTitle("", "§bInventory", 5, 10, 5);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.6f);
                }
            }

            return;

        }

        if(!player.isSneaking()) return;
        if(action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) return;
        if(cooldownPlayers.contains(player.getUniqueId())) return;
        cooldownPlayers.add(player.getUniqueId());
        event.setCancelled(true);

        playerManager.setSelectingAbility(true);
        player.sendTitle("", "§6Abilities", 5, 10, 5);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1.5f);

        ArrayList<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < 9; i++) items.add(player.getInventory().getItem(i));
        playerManager.setSavedInventory(items);
        for(int i = 0; i < 9; i++) player.getInventory().setItem(i, ItemList.FILLER);

        player.getInventory().setItem(0, ItemList.CANCEL);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            cooldownPlayers.remove(player.getUniqueId());
        }, 5);

    }

}
