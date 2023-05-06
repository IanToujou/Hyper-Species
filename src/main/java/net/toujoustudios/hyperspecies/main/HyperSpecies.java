package net.toujoustudios.hyperspecies.main;

import net.toujoustudios.hyperspecies.command.AbilityCommand;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.event.PlayerJoinListener;
import net.toujoustudios.hyperspecies.loader.Loader;
import net.toujoustudios.hyperspecies.ui.SelectSpeciesUI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HyperSpecies extends JavaPlugin {

    public static String PLUGIN_NAME = "HyperSpecies";
    public static String PLUGIN_VERSION = "1.0.0";
    public static int CONFIG_VERSION = 1;

    private static HyperSpecies instance;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        instance = this;
        this.pluginManager = Bukkit.getPluginManager();
        Loader.startLoading();
    }

    @Override
    public void onDisable() {
        PlayerManager.unloadAll();
    }

    @SuppressWarnings("all")
    public void registerCommands() {
        getCommand("ability").setExecutor(new AbilityCommand());
    }

    public void registerUI() {
        pluginManager.registerEvents(new SelectSpeciesUI(), this);
    }

    public void registerEvents() {
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public static HyperSpecies getInstance() {
        return instance;
    }

}
