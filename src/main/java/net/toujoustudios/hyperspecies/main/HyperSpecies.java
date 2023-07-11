package net.toujoustudios.hyperspecies.main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.toujoustudios.hyperspecies.command.*;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.data.team.Team;
import net.toujoustudios.hyperspecies.event.*;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.loader.Loader;
import net.toujoustudios.hyperspecies.ui.AbilityTreeUI;
import net.toujoustudios.hyperspecies.ui.ResetUI;
import net.toujoustudios.hyperspecies.ui.SpeciesUI;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public final class HyperSpecies extends JavaPlugin {

    public static String PLUGIN_NAME = "HyperSpecies";

    private static HyperSpecies instance;
    private PluginManager pluginManager;
    private Scoreboard scoreboard;

    @Override
    public void onEnable() {

        instance = this;

        this.pluginManager = Bukkit.getPluginManager();
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        assert scoreboardManager != null;
        scoreboard = scoreboardManager.getNewScoreboard();
        Loader.startLoading();

        Bukkit.getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.NATURAL_REGENERATION, false));

        // Main Thread
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.getPlayer(player);

            if(playerManager.getMana() < 0) playerManager.setMana(0);
            if(playerManager.getShield() < 0) playerManager.setShield(0);

            int scale = (int) Math.pow(10, 1);
            int alcoholScale = (int) Math.pow(10, 2);

            double health = (double) Math.round(playerManager.getHealth() * scale) / scale;
            if(health >= playerManager.getMaxHealth()) health = playerManager.getMaxHealth();
            double realHealth = (playerManager.getHealth() / playerManager.getMaxHealth()) * 20;
            double mana = (double) Math.round(playerManager.getMana() * scale) / scale;
            double shield = (double) Math.round(playerManager.getShield() * scale) / scale;
            double drunkenness = (double) Math.round((playerManager.getDrunkenness()) * alcoholScale) / alcoholScale;
            String text = "§c❤ " + health + "/" + playerManager.getMaxHealth() + " §7- §b\uD83D\uDD25 " + mana + "/" + playerManager.getMaxMana() + " §7- §e⛨ " + shield;
            if(playerManager.isDrunk()) text += " §7- §6\uD83E\uDDEA " + drunkenness + "‰";

            if(realHealth >= 20) realHealth = 20;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
            if(!player.isDead()) {
                if(realHealth > 0) player.setHealth(realHealth);
                else player.setHealth(0);
            } else {
                playerManager.setHealth(playerManager.getMaxHealth());
                playerManager.setShield(0);
                playerManager.setMana(playerManager.getMaxMana());
                playerManager.setDrunkenness(0);
            }

            player.setScoreboard(scoreboard);

            if(drunkenness >= 4) {
                player.damage(0.5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 *5, 1, false, false, true));
            } else if(drunkenness >= 3.5) {
                player.damage(0.2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 *5, 1, false, false, true));
            } else if(drunkenness >= 3.0) {
                player.damage(0.1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 *5, 1, false, false, true));
            } else if(drunkenness >= 2.5) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 *5, 1, false, false, true));
            } else if(drunkenness >= 1.8) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 0, false, false, true));
            } else if(drunkenness >= 0.8) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 5, 0, false, false, true));
            } else if(drunkenness >= 0.2) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 0, false, false, true));
            }

            if(playerManager.isDrunk()) {
                playerManager.setDrunkenness(playerManager.getDrunkenness()-0.001);
            } else playerManager.setDrunkenness(0);

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
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("tree").setExecutor(new TreeCommand());
        getCommand("shield").setExecutor(new ShieldCommand());
        getCommand("health").setExecutor(new HealthCommand());
        getCommand("mana").setExecutor(new ManaCommand());
        getCommand("species").setExecutor(new SpeciesCommand());
    }

    public void registerUI() {
        pluginManager.registerEvents(new SpeciesUI(), this);
        pluginManager.registerEvents(new TeamUI(), this);
        pluginManager.registerEvents(new ResetUI(), this);
        pluginManager.registerEvents(new AbilityTreeUI(), this);
    }

    public void registerEvents() {
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new ProjectileHitListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityRegainHealthListener(), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);
        pluginManager.registerEvents(new PlayerItemConsumeListener(), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
    }

    public void registerCrafting() {

        ShapedRecipe alcoholRecipe = new ShapedRecipe(new NamespacedKey(this, "alcoholHeineken"), ItemList.ALCOHOL_HEINEKEN);
        alcoholRecipe.shape("AAA", "ABA", "ACA");
        alcoholRecipe.setIngredient('A', Material.WHEAT);
        alcoholRecipe.setIngredient('B', Material.GLASS_BOTTLE);
        alcoholRecipe.setIngredient('C', Material.WATER_BUCKET);
        Bukkit.addRecipe(alcoholRecipe);

    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static HyperSpecies getInstance() {
        return instance;
    }

}
