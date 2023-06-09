package net.toujoustudios.hyperspecies.command;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeciesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if(!player.hasPermission("hyperspecies.command.species")) {
            player.sendMessage(Config.MESSAGE_ERROR_PERMISSION);
            return false;
        }

        if(args.length == 1) {

            Species species = Species.getSpecies(args[0]);

            if(species == null) {
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe specified species does not exist§8.");
                return false;
            }

            player.sendMessage(Config.MESSAGE_PREFIX + " §7You set your species to §b" + species.getName() + "§8.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);

            PlayerManager playerManager = PlayerManager.getPlayer(player);
            playerManager.setSpecies(species);
            playerManager.refreshScoreboard();

        } else if(args.length == 2) {

            Player target = Bukkit.getPlayer(args[1]);

            if(target == null) {
                player.sendMessage(Config.MESSAGE_ERROR_PLAYER_INVALID);
                return false;
            }

            Species species = Species.getSpecies(args[0]);

            if(species == null) {
                player.sendMessage(Config.MESSAGE_PREFIX + " §cThe specified species does not exist§8.");
                return false;
            }

            player.sendMessage(Config.MESSAGE_PREFIX + " §7You set the species of §e" + target.getName() + "§7 to §b" + species.getName() + "§8.");
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1.5f);

            PlayerManager playerManager = PlayerManager.getPlayer(target);
            playerManager.setSpecies(species);
            playerManager.refreshScoreboard();

        } else {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        return false;

    }

    public String getUsage() {
        return "/species <species> [<player>]";
    }

}
