package net.toujoustudios.hyperspecies.data.player;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.ability.Ability;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private static final HashMap<UUID, PlayerManager> players = new HashMap<>();
    private static final YamlConfiguration playerConfig = Config.getConfigFile("playerdata.yml");

    private final UUID uuid;
    private double health;
    private double maxHealth;
    private double healthRegeneration;
    private double mana;
    private double maxMana;
    private double manaRegeneration;
    private double shield;
    private double maxShield;
    private Species species;
    private boolean selectingAbility;
    private boolean regenerationCoolingDown;
    private ArrayList<ItemStack> savedInventory;
    private ArrayList<Ability> cooldownAbilities;

    public PlayerManager(UUID uuid) {

        this.uuid = uuid;
        maxHealth = 50;
        maxMana = 20;
        manaRegeneration = 0.1;
        maxShield = (double) maxHealth / 2;

        if(playerConfig.isSet("Data." + uuid + ".Stats.Health")) health = playerConfig.getDouble("Data." + uuid + ".Stats.Health");
        else health = maxHealth;
        if(playerConfig.isSet("Data." + uuid + ".Stats.Mana")) mana = playerConfig.getDouble("Data." + uuid + ".Stats.Mana");
        else mana = maxMana;
        if(playerConfig.isSet("Data." + uuid + ".Stats.Shield")) shield = playerConfig.getDouble("Data." + uuid + ".Stats.Shield");
        else shield = 0;

        selectingAbility = false;
        regenerationCoolingDown = false;
        savedInventory = new ArrayList<>();
        cooldownAbilities = new ArrayList<>();

        species = Species.getSpecies(playerConfig.getString("Data." + uuid + ".Species.Main"));

    }

    public static PlayerManager getPlayer(UUID uuid) {
        if(players.containsKey(uuid)) return players.get(uuid);
        PlayerManager playerManager = new PlayerManager(uuid);
        players.put(uuid, playerManager);
        return playerManager;
    }

    public static PlayerManager getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    // BASE METHODS

    public void unload() {
        save();
        destroy();
    }

    public void save() {
        playerConfig.set("Data." + uuid + ".Species.Main", species.getName());
        playerConfig.set("Data." + uuid + ".Species.Sub", -1);
        playerConfig.set("Data." + uuid + ".Stats.Health", health);
        playerConfig.set("Data." + uuid + ".Stats.Mana", mana);
        playerConfig.set("Data." + uuid + ".Stats.Shield", shield);
        Config.saveToFile(playerConfig, "playerdata.yml");
    }

    public static void unloadAll() {
        saveAll();
        players.clear();
    }

    public static void saveAll() {
        if(players == null || players.size() == 0) return;
        for(Map.Entry<UUID, PlayerManager> entry : players.entrySet()) {
            players.get(entry.getKey()).save();
        }
    }

    public void destroy() {
        players.remove(uuid);
    }

    // CUSTOM METHODS

    public boolean useAbility(Ability ability) {
        if(Bukkit.getPlayer(uuid) == null) return false;
        if(getCooldownAbilities().contains(ability)) return false;
        if(getMana() < ability.getManaCost()) return false;
        if(ability.execute(Bukkit.getPlayer(uuid))) {
            setMana(getMana() - ability.getManaCost());
            addCooldownAbility(ability);
            return true;
        } else return false;
    }

    public void addCooldownAbility(Ability ability) {
        this.cooldownAbilities.add(ability);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> {
            this.cooldownAbilities.remove(ability);
        }, ability.getDelay() * 20L);
    }

    // GETTERS AND SETTERS

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getHealthRegeneration() {
        return healthRegeneration;
    }

    public void setHealthRegeneration(double healthRegeneration) {
        this.healthRegeneration = healthRegeneration;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public double getManaRegeneration() {
        return manaRegeneration;
    }

    public void setManaRegeneration(double manaRegeneration) {
        this.manaRegeneration = manaRegeneration;
    }

    public double getShield() {
        return shield;
    }

    public void setShield(double shield) {
        this.shield = shield;
    }

    public double getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(double maxShield) {
        this.maxShield = maxShield;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public boolean isSelectingAbility() {
        return selectingAbility;
    }

    public void setSelectingAbility(boolean selectingAbility) {
        this.selectingAbility = selectingAbility;
    }

    public boolean isRegenerationCoolingDown() {
        return regenerationCoolingDown;
    }

    public void setRegenerationCoolingDown(boolean regenerationCoolingDown) {
        this.regenerationCoolingDown = regenerationCoolingDown;
    }

    public ArrayList<ItemStack> getSavedInventory() {
        return savedInventory;
    }

    public void setSavedInventory(ArrayList<ItemStack> savedInventory) {
        this.savedInventory = savedInventory;
    }

    public ArrayList<Ability> getCooldownAbilities() {
        return cooldownAbilities;
    }

    public void setCooldownAbilities(ArrayList<Ability> cooldownAbilities) {
        this.cooldownAbilities = cooldownAbilities;
    }

    // STATIC METHODS

    public static HashMap<UUID, PlayerManager> getPlayers() {
        return players;
    }

    public static YamlConfiguration getPlayerConfig() {
        return playerConfig;
    }

}
