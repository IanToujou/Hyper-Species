package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.ui.SpeciesUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if (playerManager.getSpecies() == null) {
            SpeciesUI.openInventory(player);
        }

        playerManager.refreshScoreboard();
        event.setJoinMessage("");

    }

}
