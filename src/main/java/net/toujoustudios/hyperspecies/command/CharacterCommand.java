package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.character.Character;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class CharacterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.character")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        if (playerManager.getSpecies() == null) return false;

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
        player.openInventory(Character.buildInventory(player));

        return false;

    }

    public String getUsage() {
        return "/character";
    }

}
