package net.toujoustudios.hyperspecies.main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.toujoustudios.hyperspecies.command.AbilityCommand;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.event.EntityDamageListener;
import net.toujoustudios.hyperspecies.event.PlayerInteractListener;
import net.toujoustudios.hyperspecies.event.PlayerJoinListener;
import net.toujoustudios.hyperspecies.event.ProjectileHitListener;
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

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            int scale = (int) Math.pow(10, 1);

            double health = (double) Math.round(playerManager.getHealth() * scale) / scale;
            double realHealth = (playerManager.getHealth() / playerManager.getMaxHealth()) * 20;
            double mana = (double) Math.round(playerManager.getMana() * scale) / scale;
            double shield = (double) Math.round(playerManager.getShield() * scale) / scale;
            String text = "§c❤ " + health + " §7- §b\uD83D\uDD25 " + mana + " §7- §e⛨ " + shield;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
            if(!player.isDead()) {
                if(realHealth > 0) player.setHealth(realHealth);
                else player.setHealth(0);
            } else {
                playerManager.setHealth(playerManager.getMaxHealth());
            }

        })), 5, 5);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.getPlayer(player);
            if(playerManager.getMana() < playerManager.getMaxMana()) {
                playerManager.setMana(playerManager.getMana() + playerManager.getManaRegeneration());
            } else {
                playerManager.setMana(playerManager.getMaxMana());
            }

        })), 20, 20);

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
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new ProjectileHitListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public static HyperSpecies getInstance() {
        return instance;
    }

}
