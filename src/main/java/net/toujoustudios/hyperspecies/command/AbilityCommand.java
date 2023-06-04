package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AbilityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) return false;
        else Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");

        if(!player.hasPermission("hyperspecies.command.ability")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if(args.length == 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        StringBuilder nameBuilder = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            nameBuilder.append(args[i].substring(0, 1).toUpperCase()).append(args[i].substring(1).toLowerCase());
            if(i != (args.length - 1)) nameBuilder.append(" ");
        }

        Ability ability = Ability.getAbility(nameBuilder.toString());

        if(ability == null) {
            player.sendMessage(Config.MESSAGE_ERROR_ABILITY_INVALID);
            return false;
        }

        ability.execute(player);
        player.sendMessage(Config.MESSAGE_PREFIX + " §7You used the ability §e" + ability.getName() + "§8.");

        return false;

    }

    public String getUsage() {
        return "/ability <name>";
    }

}
