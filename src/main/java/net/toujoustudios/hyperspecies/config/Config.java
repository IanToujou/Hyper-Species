package net.toujoustudios.hyperspecies.config;

import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    //Main plugin settings
    public static boolean USE_DATABASE;

    @SuppressWarnings("all")
    public static void initialize() {

        Logger.log(LogLevel.INFORMATION, "Loading configuration files...");
        File settingsConfigFile = new File("plugins/" + HyperSpecies.PLUGIN_NAME + "/settings.yml");

        if(settingsConfigFile.exists()) {

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(settingsConfigFile);

            if(!configuration.isSet("UseDatabase")) configuration.set("UseDatabase", false);

            try {
                configuration.save(settingsConfigFile);
            } catch(IOException exception) {
                exception.printStackTrace();
            }

        } else {

            Logger.log(LogLevel.WARNING, "Configuration file settings.yml not found. Creating now...");
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File("src/resources/" + HyperSpecies.PLUGIN_NAME + "/settings.yml"));

            if(!configuration.isSet("UseDatabase")) configuration.set("UseDatabase", false);

            try {
                configuration.save(settingsConfigFile);
            } catch(IOException exception) {
                exception.printStackTrace();
            }

        }

        YamlConfiguration settingsConfig = YamlConfiguration.loadConfiguration(settingsConfigFile);

        USE_DATABASE = settingsConfig.getBoolean("UseDatabase");

        Logger.log(LogLevel.INFORMATION, "Successfully loaded the configuration files.");

    }

    public static YamlConfiguration getConfigFile(String filename) {
        File file = new File("plugins/" + HyperSpecies.PLUGIN_NAME + "/" + filename);
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void saveToFile(YamlConfiguration configuration, String filename) {
        File file = new File("plugins/" + HyperSpecies.PLUGIN_NAME + "/" + filename);
        try {
            configuration.save(file);
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

}
