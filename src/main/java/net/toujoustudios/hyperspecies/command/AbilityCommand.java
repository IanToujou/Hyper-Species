package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class AbilityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.ability")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            nameBuilder.append(args[i].substring(0, 1).toUpperCase()).append(args[i].substring(1).toLowerCase());
            if (i != (args.length - 1)) nameBuilder.append(" ");
        }

        Ability ability = Ability.get(nameBuilder.toString());

        if (args[0].equalsIgnoreCase("ICBM")) ability = Ability.get("ICBM");

        if (ability == null) {
            player.sendMessage(Config.MESSAGE_ERROR_ABILITY_INVALID);
            return false;
        }

        ability.execute(player);
        player.sendMessage(Config.MESSAGE_PREFIX + " §7You used the ability §e" + ability.name() + "§8.");

        return false;

    }

    public String getUsage() {
        return "/ability <name>";
    }

}
