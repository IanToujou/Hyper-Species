package net.toujoustudios.hyperspecies.main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.toujoustudios.hyperspecies.command.AbilityCommand;
import net.toujoustudios.hyperspecies.command.EmoteCommand;
import net.toujoustudios.hyperspecies.command.EmoteListCommand;
import net.toujoustudios.hyperspecies.command.TeamCommand;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.team.Team;
import net.toujoustudios.hyperspecies.event.*;
import net.toujoustudios.hyperspecies.loader.Loader;
import net.toujoustudios.hyperspecies.ui.SpeciesUI;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
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

        Bukkit.getServer().getWorlds().forEach(world -> {
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        });

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            int scale = (int) Math.pow(10, 1);

            double health = (double) Math.round(playerManager.getHealth() * scale) / scale;
            if(health >= playerManager.getMaxHealth()) health = playerManager.getMaxHealth();
            double realHealth = (playerManager.getHealth() / playerManager.getMaxHealth()) * 20;
            double mana = (double) Math.round(playerManager.getMana() * scale) / scale;
            double shield = (double) Math.round(playerManager.getShield() * scale) / scale;
            String text = "§c❤ " + health + "/" + playerManager.getMaxHealth() + " §7- §b\uD83D\uDD25 " + mana + "/" + playerManager.getMaxMana() + " §7- §e⛨ " + shield;

            if(realHealth >= 20) realHealth = 20;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
            if(!player.isDead()) {
                if(realHealth > 0) player.setHealth(realHealth);
                else player.setHealth(0);
            } else {
                playerManager.setHealth(playerManager.getMaxHealth());
            }

        })), 5, 5);

        // Regeneration
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.getPlayer(player);
            if(playerManager.getMana() < playerManager.getMaxMana()) {
                playerManager.setMana(playerManager.getMana() + playerManager.getManaRegeneration());
            } else {
                playerManager.setMana(playerManager.getMaxMana());
            }

            if(!player.isDead() && playerManager.getHealth() > 0) {
                if(!playerManager.isRegenerationCoolingDown() && playerManager.getHealthRegeneration() <= 0) {
                    playerManager.setHealthRegeneration(0.2);
                }
                playerManager.setHealth(Math.min(playerManager.getHealth() + playerManager.getHealthRegeneration(), playerManager.getMaxHealth()));
            }

        })), 20, 20);

        // Passive
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.getPlayer(player);
            if(playerManager.getSpecies() == null) return;
            if(playerManager.getSpecies().getPassive() == null) return;
            playerManager.getSpecies().getPassive().execute(player);

        })), 5, 5);

    }

    @Override
    public void onDisable() {
        Team.unloadAll();
        PlayerManager.unloadAll();
    }

    @SuppressWarnings("all")
    public void registerCommands() {
        getCommand("emote").setExecutor(new EmoteCommand());
        getCommand("e").setExecutor(new EmoteCommand());
        getCommand("emotelist").setExecutor(new EmoteListCommand());
        getCommand("elist").setExecutor(new EmoteListCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("ability").setExecutor(new AbilityCommand());
    }

    public void registerUI() {
        pluginManager.registerEvents(new SpeciesUI(), this);
        pluginManager.registerEvents(new TeamUI(), this);
    }

    public void registerEvents() {
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new ProjectileHitListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityRegainHealthListener(), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public static HyperSpecies getInstance() {
        return instance;
    }

}
