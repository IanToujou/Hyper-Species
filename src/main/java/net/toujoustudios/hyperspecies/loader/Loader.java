package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.species.SpeciesInitializer;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.ui.SelectSpeciesUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Loader {

    private static LoaderState state;
    private static boolean cancelled = false;

    public static void startLoading() {
        preInitialize();
        initialize();
        postInitialize();
    }

    public static void preInitialize() {

        if(cancelled) return;

        state = LoaderState.PRE_INIT;
        Config.initialize();
        ItemList.initialize();
        SelectSpeciesUI.initialize();

        if(cancelled) return;

        Logger.log(LogLevel.DEBUG, "Pre initialization completed.");

    }

    public static void initialize() {
        if(cancelled) return;
        state = LoaderState.INIT;
        HyperSpecies.getInstance().registerUI();
        HyperSpecies.getInstance().registerEvents();
        HyperSpecies.getInstance().registerCommands();
        SpeciesInitializer.initialize();
        Logger.log(LogLevel.DEBUG, "Initialization completed.");
    }

    public static void postInitialize() {

        if(cancelled) return;
        state = LoaderState.POST_INIT;

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player != null) {
                PlayerManager playerManager = PlayerManager.getPlayer(player);
                PlayerManager.getPlayers().put(player.getUniqueId(), playerManager);
            }
        }

        Logger.log(LogLevel.DEBUG, "Post initialization completed.");
        Logger.log(LogLevel.INFORMATION, "Plugin loaded successfully.");
        state = LoaderState.END;

    }

    public static void cancel() {
        cancelled = true;
        HyperSpecies.getInstance().getServer().getPluginManager().disablePlugin(HyperSpecies.getInstance());
    }

    public static LoaderState getState() {
        return state;
    }

}
