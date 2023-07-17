package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.emote.Emote;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EmoteListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if(!player.hasPermission("hyperspecies.command.emotelist")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if(args.length != 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        player.sendMessage(Config.MESSAGE_PREFIX + " §7Below is a list of all the emotes available§8:");

        Emote.getEmotes().forEach((s, emote) -> {
            if(emote.isTargeting()) {
                player.sendMessage("§7- §d/emote " + emote.getName());
            } else player.sendMessage("§7- §b/emote " + emote.getName());
        });
        player.sendMessage(Config.MESSAGE_PREFIX + " §7Note§8: §7Emotes in §dmagenta §7need a target player§8.");

        return false;

    }

    public String getUsage() {
        return "/emotelist";
    }

}
