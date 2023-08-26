package net.toujoustudios.hyperspecies.log;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;

/**
 * A custom logging system for the plugin. Supports debug logging, and different log output levels.
 */
public class Logger {

    /**
     * Static class cannot be initialized.
     */
    private Logger() {}

    /**
     * Logs a provided message with a specific level. If the level is {@link LogLevel#DEBUG}, the logger will
     * only show something if debug more is turned on in the configuration file.
     *
     * @param level The level of the output.
     * @param message The message that should be displayed.
     */
    public static void log(LogLevel level, String message) {
        if (level == LogLevel.DEBUG) if (!Config.GENERAL_DEBUG) return;
        Bukkit.getConsoleSender().sendMessage(level.color() + "[" + HyperSpecies.PLUGIN_NAME + " - " + level + "] " + message);
    }

}
