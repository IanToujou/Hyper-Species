package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.ui.ResetUI;
import net.toujoustudios.hyperspecies.ui.SpeciesUI;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Loader {


    /**
     * Static class cannot be initialized.
     */
    private Loader() {
    }

    public static void load() {
        preInit();
        init();
        postInit();
    }

    public static void preInit() {

        Config.initialize();
        ItemListUI.initialize();
        ItemList.initialize();

        Logger.log(LogLevel.DEBUG, "Pre initialization completed.");

    }

    public static void init() {
        HyperSpecies.getInstance().registerUI();
        HyperSpecies.getInstance().registerEvents();
        HyperSpecies.getInstance().registerCommands();
        HyperSpecies.getInstance().registerCrafting();
        SpeciesLoader.initialize();
        AbilityLoader.initialize();
        TeamLoader.initialize();
        Logger.log(LogLevel.DEBUG, "Initialization completed.");
    }

    public static void postInit() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player != null) {
                PlayerManager playerManager = PlayerManager.get(player);
                PlayerManager.getPlayers().put(player.getUniqueId(), playerManager);
            }
        }

        SpeciesUI.initialize();
        TeamUI.initialize();
        ResetUI.initialize();

        Logger.log(LogLevel.DEBUG, "Post initialization completed.");
        Logger.log(LogLevel.INFORMATION, "Plugin loaded successfully.");

    }

}
