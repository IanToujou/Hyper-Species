package net.toujoustudios.hyperspecies.log;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;

public class Logger {

    public static void log(LogLevel level, String message) {
        if (level == LogLevel.DEBUG) {
            if (Config.DEBUG)
                Bukkit.getConsoleSender().sendMessage(level.getColor() + "[" + HyperSpecies.PLUGIN_NAME + " - " + level + "] " + message);
        } else
            Bukkit.getConsoleSender().sendMessage(level.getColor() + "[" + HyperSpecies.PLUGIN_NAME + " - " + level + "] " + message);
    }

}
