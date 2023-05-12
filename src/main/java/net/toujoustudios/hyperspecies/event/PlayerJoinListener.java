package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.ui.SelectSpeciesUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if(player.isOp()) player.sendMessage(Config.MESSAGE_NOTIFICATION_ADMIN_INFO);

        if(playerManager.getSpecies() == null) {
            SelectSpeciesUI.openInventory(player);
        }

    }

}
