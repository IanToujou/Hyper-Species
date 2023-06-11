package net.toujoustudios.hyperspecies.data.player;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.data.species.SubSpecies;
import net.toujoustudios.hyperspecies.data.team.Team;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class PlayerManager {

    private static final HashMap<UUID, PlayerManager> players = new HashMap<>();
    private static final YamlConfiguration playerConfig = Config.getConfigFile("players.yml");
    private final UUID uuid;
    private int experience;
    private double health;
    private double maxHealth;
    private double healthRegeneration;
    private double mana;
    private double maxMana;
    private double manaRegeneration;
    private double shield;
    private double maxShield;
    private Team team;
    private Species species;
    private SubSpecies subSpecies;
    private final List<Ability> abilities;
    private boolean selectingAbility;
    private boolean regenerationCoolingDown;
    private ArrayList<ItemStack> savedInventory;
    private ArrayList<Ability> abilityCooldowns;
    private final HashMap<String, Integer> abilityExperiences = new HashMap<>();

    public PlayerManager(UUID uuid) {

        this.uuid = uuid;

        if(playerConfig.isSet("Data." + uuid + ".Character.Experience.Main")) experience = playerConfig.getInt("Data." + uuid + ".Character.Experience.Main");
        else experience = 0;

        maxHealth = 50;
        maxMana = 20;
        manaRegeneration = 0.1;
        maxShield = maxHealth / 2;

        if(playerConfig.isSet("Data." + uuid + ".Points.Health")) health = playerConfig.getDouble("Data." + uuid + ".Points.Health");
        else health = maxHealth;
        if(playerConfig.isSet("Data." + uuid + ".Points.Mana")) mana = playerConfig.getDouble("Data." + uuid + ".Points.Mana");
        else mana = maxMana;
        if(playerConfig.isSet("Data." + uuid + ".Points.Shield")) shield = playerConfig.getDouble("Data." + uuid + ".Points.Shield");
        else shield = 0;

        selectingAbility = false;
        regenerationCoolingDown = false;
        savedInventory = new ArrayList<>();
        abilityCooldowns = new ArrayList<>();

        team = Team.getTeam(playerConfig.getString("Data." + uuid + ".Character.Team"));

        species = Species.getSpecies(playerConfig.getString("Data." + uuid + ".Character.Species"));
        if(species != null) subSpecies = species.getSubSpecies(playerConfig.getString("Data." + uuid + ".Character.SubSpecies"));
        else subSpecies = null;

        abilities = new ArrayList<>();
        abilities.add(Ability.getAbility("Meteor Strike"));

        playerConfig.getStringList("Data." + uuid + ".Character.Abilities").forEach(item -> {
            if(!abilities.contains(Ability.getAbility(item))) abilities.add(Ability.getAbility(item));
        });

        abilities.forEach(ability -> {
            if(playerConfig.contains("Data." + uuid + ".Character.Experience.Ability." + ability)) {
                abilityExperiences.put(ability.getName(), playerConfig.getInt("Data." + uuid + ".Character.Experience.Ability." + ability));
            } else {
                abilityExperiences.put(ability.getName(), 0);
            }
        });

        refreshScoreboard();

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

    public void save() {

        playerConfig.set("Data." + uuid + ".Points.Health", health);
        playerConfig.set("Data." + uuid + ".Points.Mana", mana);
        playerConfig.set("Data." + uuid + ".Points.Shield", shield);
        playerConfig.set("Data." + uuid + ".Character.Experience.Main", experience);
        abilityExperiences.forEach((ability, integer) -> playerConfig.set("Data." + uuid + ".Character.Experience.Ability." + ability, integer));
        playerConfig.set("Data." + uuid + ".Character.Species", (species != null ? species.getName() : null));
        playerConfig.set("Data." + uuid + ".Character.Team", (team != null ? team.getName() : null));
        playerConfig.set("Data." + uuid + ".Character.SubSpecies", (subSpecies != null ? subSpecies.getName() : null));
        ArrayList<String> names = new ArrayList<>();
        abilities.forEach(ability -> names.add(ability.getName()));
        playerConfig.set("Data." + uuid + ".Character.Abilities", names);

        Config.saveToFile(playerConfig, "players.yml");

    }

    public void destroy() {
        players.remove(uuid);
    }

    public static void unloadAll() {
        if(players == null || players.size() == 0) return;
        for(Map.Entry<UUID, PlayerManager> entry : players.entrySet()) {
            players.get(entry.getKey()).save();
            players.get(entry.getKey()).destroy();
        }
    }

    // CUSTOM METHODS

    public boolean useAbility(Ability ability) {
        if(Bukkit.getPlayer(uuid) == null) return false;
        if(getCooldownAbilities().contains(ability)) return false;
        if(getMana() < ability.getManaCost()) return false;
        if(ability.execute(Bukkit.getPlayer(uuid))) {
            setMana(getMana() - ability.getManaCost());
            addAbilityCooldown(ability);
            return true;
        } else return false;
    }

    public void addAbilityCooldown(Ability ability) {
        abilityCooldowns.add(ability);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> abilityCooldowns.remove(ability), ability.getDelay() * 20L);
    }

    public void removeAbilityCooldown(Ability ability) {
        abilityCooldowns.remove(ability);
    }

    public int getLevelFromExperience(int experience) {
        int level = 0;
        while(true) {
            int threshold = (20 + 10 * level ) * 2;
            if(experience >= threshold) level++;
            else break;
        }
        return level;
    }

    @SuppressWarnings("all")
    public void refreshScoreboard() {

        if(Bukkit.getPlayer(uuid) == null) return;

        Scoreboard board = HyperSpecies.getInstance().getScoreboard();

        if(board.getTeam(uuid.toString()) != null && board.getTeams().size() > 0) {
            board.getTeam(uuid.toString()).unregister();
        }

        final org.bukkit.scoreboard.Team scoreboardTeam = HyperSpecies.getInstance().getScoreboard().registerNewTeam(uuid.toString());
        scoreboardTeam.setColor(ChatColor.GRAY);
        scoreboardTeam.setPrefix((species!=null ? species.getPrefix() : "§7None") + " §7| ");
        scoreboardTeam.addEntry(Bukkit.getPlayer(uuid).getName());

    }

    // GETTERS AND SETTERS

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public SubSpecies getSubSpecies() {
        return subSpecies;
    }

    public void setSubSpecies(SubSpecies subSpecies) {
        this.subSpecies = subSpecies;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void addAbility(Ability ability) {
        if(!abilities.contains(ability)) abilities.add(ability);
    }

    public void removeAbility(Ability ability) {
        abilities.remove(ability);
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
        return abilityCooldowns;
    }

    public void setCooldownAbilities(ArrayList<Ability> cooldownAbilities) {
        this.abilityCooldowns = cooldownAbilities;
    }

    public HashMap<String, Integer> getAbilityExperiences() {
        return abilityExperiences;
    }

    public int getAbilityExperience(Ability ability) {
        return abilityExperiences.getOrDefault(ability.getName(), 0);
    }

    public void addAbilityExperience(Ability ability, int experience) {
        int oldExperience = abilityExperiences.getOrDefault(ability.getName(), 0);
        abilityExperiences.remove(ability.getName());
        abilityExperiences.put(ability.getName(), oldExperience + experience);
    }

    // STATIC METHODS

    public static HashMap<UUID, PlayerManager> getPlayers() {
        return players;
    }

    public static YamlConfiguration getPlayerConfig() {
        return playerConfig;
    }

}
