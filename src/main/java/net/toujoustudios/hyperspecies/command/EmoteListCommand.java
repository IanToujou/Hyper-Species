package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.emote.Emote;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmoteListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) return false;

        if(!player.hasPermission("hyperspecies.command.emotelist")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if(args.length != 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        player.sendMessage(Config.MESSAGE_INFO_EMOTE_LIST);

        Emote.getEmotes().forEach((s, emote) -> {
            if(emote.isTargeting()) {
                player.sendMessage("§7- §d/emote " + emote.getName());
            } else player.sendMessage("§7- §b/emote " + emote.getName());
        });
        player.sendMessage(Config.MESSAGE_PREFIX + " §7Note§8: §7Emotes listed in §dmagenta §7need a target player§8.");

        return false;

    }

    public String getUsage() {
        return "/emotelist";
    }

}
