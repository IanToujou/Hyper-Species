package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.emote.Emote;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class EmoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if(!player.hasPermission("hyperspecies.command.emote")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if(args.length == 0 || args.length > 2) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        Emote emote = Emote.getEmoteByName(args[0].toLowerCase(Locale.ROOT));
        if(emote == null) {
            player.sendMessage(Config.MESSAGE_ERROR_EMOTE_INVALID);
            return false;
        }

        if(emote.isTargeting() && args.length != 2) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        if(emote.isTargeting()) {

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                player.sendMessage(Config.MESSAGE_ERROR_PLAYER_INVALID);
                return false;
            }
            if(target == player) {
                player.sendMessage(Config.MESSAGE_ERROR_PLAYER_SELF);
                return false;
            }

            if(!emote.execute(player, target)) player.sendMessage(Config.MESSAGE_ERROR_EMOTE_PERFORM);
            return false;

        } else {
            if(!emote.execute(player, null)) player.sendMessage(Config.MESSAGE_ERROR_EMOTE_PERFORM);
        }

        return false;

    }

    public String getUsage() {
        return "/emote <emote> [<player>]";
    }

}
