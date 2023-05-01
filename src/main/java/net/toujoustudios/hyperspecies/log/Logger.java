package net.toujoustudios.hyperspecies.log;

import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;

public class Logger {

    public static void log(LogLevel level, String message) {
        Bukkit.getConsoleSender().sendMessage(level.getColor() + "[" + HyperSpecies.PLUGIN_NAME + " - " + level + "] " + message);
    }

}
