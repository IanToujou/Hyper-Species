package net.toujoustudios.hyperspecies.main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.toujoustudios.hyperspecies.command.*;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.event.*;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.loader.Loader;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import net.toujoustudios.hyperspecies.team.Team;
import net.toujoustudios.hyperspecies.ui.*;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
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
        Loader.load();

        Bukkit.getServer().getWorlds().forEach(world -> {
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        });

        // Main Thread
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.get(player);

            if (!playerManager.isSelectingAbility()) {
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    ItemStack itm = player.getInventory().getItem(i);
                    if (itm != null && (itm.getType() == Material.BARRIER || itm.getType() == Material.ELYTRA)) {
                        int amt = itm.getAmount() - 1;
                        itm.setAmount(0);
                        player.getInventory().setItem(i, amt > 0 ? itm : null);
                        break;
                    }
                }
            }

            if (playerManager.getSpecies() == null) {
                if (player.getOpenInventory().getType() == InventoryType.PLAYER)
                    player.sendMessage(Config.MESSAGE_PREFIX + "§c Please rejoin and select a species§8.");
                return;
            }

            if (playerManager.getMana() < 0) playerManager.setMana(0);
            if (playerManager.getShield() < 0) playerManager.setShield(0);

            int scale = (int) Math.pow(10, 1);
            int alcoholScale = (int) Math.pow(10, 2);

            double health = (double) Math.round(playerManager.getHealth() * scale) / scale;
            if (health >= playerManager.getMaxHealth()) health = playerManager.getMaxHealth();
            double realHealth = (playerManager.getHealth() / playerManager.getMaxHealth()) * 20;
            double mana = (double) Math.round(playerManager.getMana() * scale) / scale;
            double shield = (double) Math.round(playerManager.getShield() * scale) / scale;
            double drunkenness = (double) Math.round((playerManager.getDrunkenness()) * alcoholScale) / alcoholScale;
            String text = "§c❤ " + health + "/" + playerManager.getMaxHealth() + " §7- §b\uD83D\uDD25 " + mana + "/" + playerManager.getMaxMana() + " §7- §e⛨ " + shield;
            if (playerManager.isDrunk()) text += " §7- §6\uD83E\uDDEA " + drunkenness + "‰";

            if (realHealth >= 20) realHealth = 20;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
            if (!player.isDead()) {
                if (realHealth > 0) player.setHealth(realHealth);
                else {
                    player.setHealth(0);
                    for (int i = 0; i < 9; i++)
                        player.getInventory().setItem(i, playerManager.getSavedInventory().get(i));
                    playerManager.setSelectingAbility(false);
                }
            } else {
                playerManager.setHealth(playerManager.getMaxHealth());
                playerManager.setShield(0);
                playerManager.setMana(playerManager.getMaxMana());
                playerManager.setDrunkenness(0);
                playerManager.setSelectingAbility(false);
            }

            player.setScoreboard(scoreboard);

            boolean isDwarf = playerManager.getSpecies().name().equals("Dwarf");

            if (drunkenness >= 4 && !isDwarf) {
                player.damage(0.5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 2, false, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0, false, false, true));
            } else if (drunkenness >= 3.5 && !isDwarf) {
                player.damage(0.2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 2, false, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0, false, false, true));
            } else if (drunkenness >= 3.0 && !isDwarf) {
                player.damage(0.1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1, false, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0, false, false, true));
            } else if (drunkenness >= 2.5 && !isDwarf) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1, false, false, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0, false, false, true));
            } else if (drunkenness >= 1.8 && !isDwarf) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 0, false, false, true));
            } else if (drunkenness >= 0.8) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 5, 0, false, false, true));
            } else if (drunkenness >= 0.2) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 0, false, false, true));
            }

            if (playerManager.isDrunk()) {
                playerManager.setDrunkenness(playerManager.getDrunkenness() - 0.001);
            } else playerManager.setDrunkenness(0);

            if (playerManager.getShield() > playerManager.getMaxHealth() / 2)
                playerManager.setShield(playerManager.getMaxHealth() / 2);

        })), 5, 5);

        // Regeneration
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.get(player);
            if (playerManager.getMana() < playerManager.getMaxMana()) {
                playerManager.setMana(playerManager.getMana() + playerManager.getManaRegeneration());
            } else playerManager.setMana(playerManager.getMaxMana());

            if (!player.isDead() && playerManager.getHealth() > 0 && player.getFoodLevel() > 15) {
                if (!playerManager.isRegenerationCoolingDown() && playerManager.getHealthRegeneration() <= 0) {
                    playerManager.setHealthRegeneration(0.2);
                }
                playerManager.setHealth(Math.min(playerManager.getHealth() + playerManager.getHealthRegeneration(), playerManager.getMaxHealth()));
            }

        })), 20, 20);

        // Passive
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {

            PlayerManager playerManager = PlayerManager.get(player);
            if (playerManager.getSpecies() == null) return;
            if (playerManager.getSpecies().passive() == null) return;
            playerManager.getSpecies().passive().execute(player);

        })), 5, 5);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, PlayerManager::saveAll, 20 * 600, 20 * 600);

    }

    @Override
    public void onDisable() {
        Team.unloadAll();
        PlayerManager.unloadAll();
    }

    @SuppressWarnings("all")
    public void registerCommands() {
        getCommand("species").setExecutor(new SpeciesCommand());
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("ability").setExecutor(new AbilityCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("loadout").setExecutor(new LoadoutCommand());
        getCommand("lo").setExecutor(new LoadoutCommand());
        getCommand("setshield").setExecutor(new SetShieldCommand());
        getCommand("sethealth").setExecutor(new SetHealthCommand());
        getCommand("setmana").setExecutor(new SetManaCommand());
        getCommand("setspecies").setExecutor(new SetSpeciesCommand());
        getCommand("unlock").setExecutor(new UnlockCommand());
        getCommand("setskill").setExecutor(new SetSkillCommand());
        getCommand("setexperience").setExecutor(new SetExperienceCommand());
        getCommand("character").setExecutor(new CharacterCommand());
        getCommand("char").setExecutor(new CharacterCommand());
        getCommand("leave").setExecutor(new LeaveCommand());
        getCommand("channel").setExecutor(new ChannelCommand());
        getCommand("c").setExecutor(new ChannelCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("tell").setExecutor(new MessageCommand());
        getCommand("msg").setExecutor(new MessageCommand());
    }

    public void registerUI() {
        pluginManager.registerEvents(new SpeciesUI(), this);
        pluginManager.registerEvents(new TeamUI(), this);
        pluginManager.registerEvents(new ResetUI(), this);
        pluginManager.registerEvents(new AbilityTreeUI(), this);
        pluginManager.registerEvents(new CharacterUI(), this);
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
        pluginManager.registerEvents(new PlayerDropItemListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new EntityPickupItemListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
    }

    public void registerCrafting() {

        ShapedRecipe alcoholBeerRecipe = new ShapedRecipe(new NamespacedKey(this, "beer"), ItemList.BEER);
        alcoholBeerRecipe.shape("AAA", "ABA", "ACA");
        alcoholBeerRecipe.setIngredient('A', Material.WHEAT);
        alcoholBeerRecipe.setIngredient('B', Material.GLASS_BOTTLE);
        alcoholBeerRecipe.setIngredient('C', Material.WATER_BUCKET);
        Bukkit.addRecipe(alcoholBeerRecipe);

        ShapedRecipe alcoholRumRecipe = new ShapedRecipe(new NamespacedKey(this, "rum"), ItemList.RUM);
        alcoholRumRecipe.shape("AAA", "ABA", "DCD");
        alcoholRumRecipe.setIngredient('A', Material.SUGAR_CANE);
        alcoholRumRecipe.setIngredient('B', Material.GLASS_BOTTLE);
        alcoholRumRecipe.setIngredient('C', Material.WATER_BUCKET);
        alcoholRumRecipe.setIngredient('D', Material.SUGAR);
        Bukkit.addRecipe(alcoholRumRecipe);

        ShapedRecipe alcoholRedWineRecipe = new ShapedRecipe(new NamespacedKey(this, "redWine"), ItemList.RED_WINE);
        alcoholRedWineRecipe.shape("AAA", "ABA", "ACA");
        alcoholRedWineRecipe.setIngredient('A', Material.SWEET_BERRIES);
        alcoholRedWineRecipe.setIngredient('B', Material.GLASS_BOTTLE);
        alcoholRedWineRecipe.setIngredient('C', Material.WATER_BUCKET);
        Bukkit.addRecipe(alcoholRedWineRecipe);

        ShapedRecipe alcoholWhiteWineRecipe = new ShapedRecipe(new NamespacedKey(this, "whiteWine"), ItemList.WHITE_WINE);
        alcoholWhiteWineRecipe.shape("AAA", "ABA", "ACA");
        alcoholWhiteWineRecipe.setIngredient('A', Material.GLOW_BERRIES);
        alcoholWhiteWineRecipe.setIngredient('B', Material.GLASS_BOTTLE);
        alcoholWhiteWineRecipe.setIngredient('C', Material.WATER_BUCKET);
        Bukkit.addRecipe(alcoholWhiteWineRecipe);

        ShapedRecipe meadRecipe = new ShapedRecipe(new NamespacedKey(this, "mead"), ItemList.MEAD);
        meadRecipe.shape("AAA", "ABA", "DCD");
        meadRecipe.setIngredient('A', Material.HONEYCOMB);
        meadRecipe.setIngredient('B', Material.GLASS_BOTTLE);
        meadRecipe.setIngredient('C', Material.WATER_BUCKET);
        meadRecipe.setIngredient('D', Material.HONEYCOMB_BLOCK);
        Bukkit.addRecipe(meadRecipe);

        ShapelessRecipe molotovCocktailRecipe = new ShapelessRecipe(new NamespacedKey(this, "molotovCocktail"), ItemList.MOLOTOV_COCKTAIL);
        molotovCocktailRecipe.addIngredient(Material.GLASS_BOTTLE);
        molotovCocktailRecipe.addIngredient(Material.PAPER);
        molotovCocktailRecipe.addIngredient(Material.LAVA_BUCKET);
        Bukkit.addRecipe(molotovCocktailRecipe);

    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static HyperSpecies getInstance() {
        return instance;
    }

}
