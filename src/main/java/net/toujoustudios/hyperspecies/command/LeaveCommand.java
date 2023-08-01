package net.toujoustudios.hyperspecies.command;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.character.Character;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LeaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if (!player.hasPermission("hyperspecies.command.leave")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if (args.length != 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        World world = Bukkit.getWorld("world");

        if(world == null) {
            player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§c The main world is not available§8."));
            return false;
        }

        if(player.getWorld().getName().equals("farmworld") || player.getWorld().getName().equals("farmworld_nether")) {
            if(player.getBedSpawnLocation() != null && (player.getBedSpawnLocation().getWorld().getName().equals("world") || player.getBedSpawnLocation().getWorld().getName().equals("world_nether"))) {
                player.teleport(player.getBedSpawnLocation());
            } else player.teleport(world.getSpawnLocation());
        } else player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§c You are not in the farmworld§8."));

        return false;

    }

    public String getUsage() {
        return "/leave";
    }

}
