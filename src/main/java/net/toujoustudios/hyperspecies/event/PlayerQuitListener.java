package net.toujoustudios.hyperspecies.event;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        playerManager.save();
        event.quitMessage(Component.text(""));

    }

}
