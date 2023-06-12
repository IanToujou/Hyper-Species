package net.toujoustudios.hyperspecies.config;

import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    // General
    public static boolean DEBUG;

    // Message
    public static String MESSAGE_PREFIX;
    public static String MESSAGE_PREFIX_ROLEPLAY;
    public static String MESSAGE_ERROR_PERMISSION;
    public static String MESSAGE_ERROR_SYNTAX;
    public static String MESSAGE_ERROR_PLAYER_INVALID;
    public static String MESSAGE_ERROR_PLAYER_SELF;
    public static String MESSAGE_ERROR_EMOTE_INVALID;
    public static String MESSAGE_ERROR_EMOTE_PERFORM;
    public static String MESSAGE_ERROR_ABILITY_INVALID;
    public static String MESSAGE_ERROR_NO_SPECIES;

    @SuppressWarnings("all")
    public static void initialize() {

        Logger.log(LogLevel.INFORMATION, "Loading configuration files...");
        File settingsConfigFile = new File("plugins/" + HyperSpecies.PLUGIN_NAME + "/settings.yml");

        if(settingsConfigFile.exists()) {

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(settingsConfigFile);

            if(!configuration.isSet("General.Debug")) configuration.set("General.Debug", false);
            if(!configuration.isSet("Message.Prefix")) configuration.set("Message.Prefix", "§2HyperSpecies §8|");
            if(!configuration.isSet("Message.PrefixRoleplay")) configuration.set("Message.PrefixRoleplay", "§6RP §8|");
            if(!configuration.isSet("Message.Error.Permission")) configuration.set("Message.Error.Permission", "{Prefix} §cYou do not have the permission to perform this command§8.");
            if(!configuration.isSet("Message.Error.Syntax")) configuration.set("Message.Error.Syntax", "{Prefix} §cThe command syntax is not correct§8. §cUsage§8: §e{Usage}");
            if(!configuration.isSet("Message.Error.PlayerInvalid")) configuration.set("Message.Error.PlayerInvalid", "{Prefix} §cThe given player is invalid§8.");
            if(!configuration.isSet("Message.Error.PlayerSelf")) configuration.set("Message.Error.PlayerSelf", "{Prefix} §cYou cannot do that to yourself§8.");
            if(!configuration.isSet("Message.Error.EmoteInvalid")) configuration.set("Message.Error.EmoteInvalid", "{Prefix} §cThe selected emote is invalid§8.");
            if(!configuration.isSet("Message.Error.EmotePerform")) configuration.set("Message.Error.EmotePerform", "{Prefix} §cThe emote could not be performed§8.");
            if(!configuration.isSet("Message.Error.AbilityInvalid")) configuration.set("Message.Error.AbilityInvalid", "{Prefix} §cThe ability does not exist§8.");
            if(!configuration.isSet("Message.Error.NoSpecies")) configuration.set("Message.Error.NoSpecies", "§cYou need to select a species in order to continue.");

            try {
                configuration.save(settingsConfigFile);
            } catch(IOException exception) {
                exception.printStackTrace();
            }

        } else {

            Logger.log(LogLevel.WARNING, "Configuration file settings.yml not found. Creating now...");
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File("src/resources/" + HyperSpecies.PLUGIN_NAME + "/settings.yml"));

            if(!configuration.isSet("General.Debug")) configuration.set("General.Debug", false);
            if(!configuration.isSet("Message.Prefix")) configuration.set("Message.Prefix", "§2HyperSpecies §8|");
            if(!configuration.isSet("Message.PrefixRoleplay")) configuration.set("Message.PrefixRoleplay", "§6RP §8|");
            if(!configuration.isSet("Message.Error.Permission")) configuration.set("Message.Error.Permission", "{Prefix} §cYou do not have the permission to perform this command§8.");
            if(!configuration.isSet("Message.Error.Syntax")) configuration.set("Message.Error.Syntax", "{Prefix} §cThe command syntax is not correct§8. §cUsage§8: §e{Usage}");
            if(!configuration.isSet("Message.Error.PlayerInvalid")) configuration.set("Message.Error.PlayerInvalid", "{Prefix} §cThe given player is invalid§8.");
            if(!configuration.isSet("Message.Error.PlayerSelf")) configuration.set("Message.Error.PlayerSelf", "{Prefix} §cYou cannot do that to yourself§8.");
            if(!configuration.isSet("Message.Error.EmoteInvalid")) configuration.set("Message.Error.EmoteInvalid", "{Prefix} §cThe selected emote is invalid§8.");
            if(!configuration.isSet("Message.Error.EmotePerform")) configuration.set("Message.Error.EmotePerform", "{Prefix} §cThe emote could not be performed§8.");
            if(!configuration.isSet("Message.Error.AbilityInvalid")) configuration.set("Message.Error.AbilityInvalid", "{Prefix} §cThe ability does not exist§8.");
            if(!configuration.isSet("Message.Error.NoSpecies")) configuration.set("Message.Error.NoSpecies", "§cYou need to select a species in order to continue.");

            try {
                configuration.save(settingsConfigFile);
            } catch(IOException exception) {
                exception.printStackTrace();
            }

        }

        YamlConfiguration settingsConfig = YamlConfiguration.loadConfiguration(settingsConfigFile);

        DEBUG = settingsConfig.getBoolean("General.Debug");
        MESSAGE_PREFIX = settingsConfig.getString("Message.Prefix");
        MESSAGE_PREFIX_ROLEPLAY = settingsConfig.getString("Message.PrefixRoleplay");
        MESSAGE_ERROR_PERMISSION = settingsConfig.getString("Message.Error.Permission").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_SYNTAX = settingsConfig.getString("Message.Error.Syntax").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_PLAYER_INVALID = settingsConfig.getString("Message.Error.PlayerInvalid").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_PLAYER_SELF = settingsConfig.getString("Message.Error.PlayerSelf").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_EMOTE_INVALID = settingsConfig.getString("Message.Error.EmoteInvalid").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_EMOTE_PERFORM = settingsConfig.getString("Message.Error.EmotePerform").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_ABILITY_INVALID = settingsConfig.getString("Message.Error.AbilityInvalid").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_NO_SPECIES = settingsConfig.getString("Message.Error.NoSpecies");

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
