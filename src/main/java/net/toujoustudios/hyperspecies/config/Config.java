package net.toujoustudios.hyperspecies.config;

import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    // General
    public static boolean GENERAL_DEBUG;

    // Message
    public static String MESSAGE_PREFIX;
    public static String MESSAGE_ERROR_PERMISSION;
    public static String MESSAGE_ERROR_SYNTAX;
    public static String MESSAGE_ERROR_PLAYER_INVALID;
    public static String MESSAGE_ERROR_PLAYER_SELF;
    public static String MESSAGE_ERROR_INTEGER_INVALID;
    public static String MESSAGE_ERROR_EMOTE_INVALID;
    public static String MESSAGE_ERROR_EMOTE_PERFORM;
    public static String MESSAGE_ERROR_ABILITY_INVALID;
    public static String MESSAGE_ERROR_NO_SPECIES;

    @SuppressWarnings("all")
    public static void initialize() {

        Logger.log(LogLevel.INFORMATION, "Loading configuration files...");
        File generalConfigFile = new File("plugins/" + HyperSpecies.PLUGIN_NAME + "/config/general.yml");
        File messagesConfigFile = new File("plugins/" + HyperSpecies.PLUGIN_NAME + "/config/messages.yml");

        YamlConfiguration generalConfig = YamlConfiguration.loadConfiguration(generalConfigFile);
        YamlConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesConfigFile);

        setIfAbsent(generalConfig, "General.Debug", false);

        setIfAbsent(messagesConfig, "Message.Prefix", "§2HyperSpecies §8|");
        setIfAbsent(messagesConfig, "Message.Error.Permission", "{Prefix} §cYou do not have the permission to perform this command§8.");
        setIfAbsent(messagesConfig, "Message.Error.Syntax", "{Prefix} §cThe command syntax is not correct§8. §cUsage§8: §e{Usage}");
        setIfAbsent(messagesConfig, "Message.Error.PlayerInvalid", "{Prefix} §cThe given player is invalid§8.");
        setIfAbsent(messagesConfig, "Message.Error.PlayerSelf", "{Prefix} §cYou cannot do that to yourself§8.");
        setIfAbsent(messagesConfig, "Message.Error.IntegerInvalid", "{Prefix} §cThe given value is not a valid integer number§8.");
        setIfAbsent(messagesConfig, "Message.Error.EmoteInvalid", "{Prefix} §cThe selected emote is invalid§8.");
        setIfAbsent(messagesConfig, "Message.Error.EmotePerform", "{Prefix} §cThe emote could not be performed§8.");
        setIfAbsent(messagesConfig, "Message.Error.AbilityInvalid", "{Prefix} §cThe ability does not exist§8.");
        setIfAbsent(messagesConfig, "Message.Error.NoSpecies", "§cYou need to select a species in order to continue.");

        try {
            generalConfig.save(generalConfigFile);
            messagesConfig.save(messagesConfigFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        GENERAL_DEBUG = generalConfig.getBoolean("General.Debug");

        MESSAGE_PREFIX = messagesConfig.getString("Message.Prefix");
        MESSAGE_ERROR_PERMISSION = messagesConfig.getString("Message.Error.Permission").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_SYNTAX = messagesConfig.getString("Message.Error.Syntax").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_PLAYER_INVALID = messagesConfig.getString("Message.Error.PlayerInvalid").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_PLAYER_SELF = messagesConfig.getString("Message.Error.PlayerSelf").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_INTEGER_INVALID = messagesConfig.getString("Message.Error.IntegerInvalid").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_EMOTE_INVALID = messagesConfig.getString("Message.Error.EmoteInvalid").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_EMOTE_PERFORM = messagesConfig.getString("Message.Error.EmotePerform").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_ABILITY_INVALID = messagesConfig.getString("Message.Error.AbilityInvalid").replace("{Prefix}", MESSAGE_PREFIX);
        MESSAGE_ERROR_NO_SPECIES = messagesConfig.getString("Message.Error.NoSpecies");

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
        } catch (IOException exception) {
            Logger.log(LogLevel.ERROR, "Failed to write config file " + file.getName());
        }
    }

    private static void setIfAbsent(YamlConfiguration configuration, String field, Object value) {
        if(!configuration.isSet(field)) configuration.set(field, value);
    }

}
