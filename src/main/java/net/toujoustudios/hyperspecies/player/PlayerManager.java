package net.toujoustudios.hyperspecies.player;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.chat.ChatChannel;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import net.toujoustudios.hyperspecies.species.Species;
import net.toujoustudios.hyperspecies.species.SubSpecies;
import net.toujoustudios.hyperspecies.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class PlayerManager {

    private static final HashMap<UUID, PlayerManager> players = new HashMap<>();
    private static final YamlConfiguration playerConfig = Config.getConfigFile("data/players.yml");

    private final UUID uuid;
    private String name;
    private int experience;
    private int skill;
    private double health;
    private double healthRegeneration;
    private double mana;
    private double manaRegeneration;
    private double shield;
    private String team;
    private Species species;
    private SubSpecies subSpecies;
    private final List<Ability> abilities;
    private final List<Ability> activeAbilities;
    private boolean selectingAbility;
    private boolean regenerationCoolingDown;
    private ArrayList<ItemStack> savedInventory;
    private final ArrayList<Ability> abilityCooldowns;
    private final HashMap<String, Integer> abilityExperiences = new HashMap<>();
    private double drunkenness;
    private boolean stunned;
    private ChatChannel channel;
    private boolean kawaii;

    public PlayerManager(UUID uuid) {

        this.uuid = uuid;

        name = playerConfig.getString("Data." + uuid + ".Character.Name");
        experience = playerConfig.getInt("Data." + uuid + ".Character.Experience.Main");
        skill = playerConfig.getInt("Data." + uuid + ".Character.Skill");

        manaRegeneration = 0.1;

        if (playerConfig.isSet("Data." + uuid + ".Points.Health"))
            health = playerConfig.getDouble("Data." + uuid + ".Points.Health");
        else health = getMaxHealth();
        if (playerConfig.isSet("Data." + uuid + ".Points.Mana"))
            mana = playerConfig.getDouble("Data." + uuid + ".Points.Mana");
        else mana = getMaxMana();
        shield = playerConfig.getDouble("Data." + uuid + ".Points.Shield");

        selectingAbility = false;
        regenerationCoolingDown = false;
        savedInventory = new ArrayList<>();
        abilityCooldowns = new ArrayList<>();

        team = playerConfig.getString("Data." + uuid + ".Character.Team");

        species = Species.getSpecies(playerConfig.getString("Data." + uuid + ".Character.Species"));
        if (species != null)
            subSpecies = species.getSubSpecies(playerConfig.getString("Data." + uuid + ".Character.SubSpecies"));
        else subSpecies = null;

        abilities = new ArrayList<>();
        playerConfig.getStringList("Data." + uuid + ".Character.Abilities").forEach(item -> {
            if (!abilities.contains(Ability.get(item))) abilities.add(Ability.get(item));
        });

        activeAbilities = new ArrayList<>();
        playerConfig.getStringList("Data." + uuid + ".Character.ActiveAbilities").forEach(item -> {
            if (!activeAbilities.contains(Ability.get(item))) activeAbilities.add(Ability.get(item));
        });

        abilities.forEach(ability -> abilityExperiences.put(ability.name(), playerConfig.getInt("Data." + uuid + ".Character.Experience.Ability." + ability.name())));

        refreshScoreboard();

        drunkenness = playerConfig.getDouble("Data." + uuid + ".Points.Drunkenness");

        stunned = false;
        channel = ChatChannel.LOCAL;
        kawaii = false;

    }

    public static PlayerManager get(UUID uuid) {
        if (players.containsKey(uuid)) return players.get(uuid);
        PlayerManager playerManager = new PlayerManager(uuid);
        players.put(uuid, playerManager);
        return playerManager;
    }

    public static PlayerManager get(Player player) {
        return get(player.getUniqueId());
    }

    /**
     * Saves all the data that is stored in the in-memory hashmap to the config file.
     * This method does NOT clear any in-memory data.
     *
     * @see PlayerManager#destroy()
     * @since 1.0.0
     */
    public void save() {

        playerConfig.set("Data." + uuid + ".Character.Name", name);
        playerConfig.set("Data." + uuid + ".Points.Health", health);
        playerConfig.set("Data." + uuid + ".Points.Mana", mana);
        playerConfig.set("Data." + uuid + ".Points.Shield", shield);
        playerConfig.set("Data." + uuid + ".Points.Drunkenness", drunkenness);
        playerConfig.set("Data." + uuid + ".Character.Experience.Main", experience);
        playerConfig.set("Data." + uuid + ".Character.Skill", skill);
        abilityExperiences.forEach((ability, integer) -> playerConfig.set("Data." + uuid + ".Character.Experience.Ability." + ability, integer));
        playerConfig.set("Data." + uuid + ".Character.Species", (species != null ? species.name() : null));
        playerConfig.set("Data." + uuid + ".Character.Team", team);
        playerConfig.set("Data." + uuid + ".Character.SubSpecies", (subSpecies != null ? subSpecies.name() : null));
        ArrayList<String> abilityNames = new ArrayList<>();
        abilities.forEach(ability -> abilityNames.add(ability.name()));
        playerConfig.set("Data." + uuid + ".Character.Abilities", abilityNames);
        ArrayList<String> activeAbilityNames = new ArrayList<>();
        activeAbilities.forEach(ability -> activeAbilityNames.add(ability.name()));
        playerConfig.set("Data." + uuid + ".Character.ActiveAbilities", activeAbilityNames);

        Config.saveToFile(playerConfig, "data/players.yml");

    }

    /**
     * Destroys the data stored in the in-memory hashmap.
     * This method does NOT save the data. It simply removes it from the memory.
     *
     * @see PlayerManager#save()
     * @since 1.0.0
     */
    public void destroy() {
        players.remove(uuid);
    }

    public static void unloadAll() {
        saveAll();
        players.clear();
    }

    public static void saveAll() {
        if (players == null || players.isEmpty()) return;
        for (Map.Entry<UUID, PlayerManager> entry : players.entrySet()) {
            players.get(entry.getKey()).save();
        }
    }

    // CUSTOM METHODS

    public boolean useAbility(Ability ability) {
        if (Bukkit.getPlayer(uuid) == null) return false;
        if (getCooldownAbilities().contains(ability)) return false;
        if (getMana() < ability.manaCost()) return false;
        if (getAbilityLevel(ability) > ability.maxLevel()) setAbilityLevel(ability, ability.maxLevel());
        if (ability.execute(Bukkit.getPlayer(uuid))) {
            setMana(getMana() - ability.manaCost());
            abilityCooldowns.add(ability);
            if (getAbilityLevel(ability) < ability.maxLevel()) addAbilityExperience(ability, 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> abilityCooldowns.remove(ability), ability.delay() * 20L);
            return true;
        } else return false;
    }

    public int getLevelFromExperience(int experience) {
        int level = 0;
        while (true) {
            int threshold = getLevelThreshold(level);
            if (experience >= threshold) level++;
            else break;
        }
        return level;
    }

    public int getExperienceSinceLastLevel(int experience) {
        int level = 0;
        while (true) {
            int threshold = getLevelThreshold(level);
            if (experience >= threshold) level++;
            else {
                if (level == 0) return experience;
                else return (experience - getLevelThreshold(level - 1));
            }
        }
    }

    public int getLevelThreshold(int level) {
        if (level == 0) return 20;
        return (20 + 10 * level) * level;
    }

    public int getRelativeLevelThreshold(int level) {
        int threshold = getLevelThreshold(level);
        if (level == 0) return threshold;
        return threshold - getLevelThreshold(level - 1);
    }

    public void refreshScoreboard() {

        if (Bukkit.getPlayer(uuid) == null) return;

        Scoreboard board = HyperSpecies.getInstance().getScoreboard();

        if (board.getTeam(uuid.toString()) != null && !board.getTeams().isEmpty()) {
            Objects.requireNonNull(board.getTeam(uuid.toString())).unregister();
        }

        final org.bukkit.scoreboard.Team scoreboardTeam = HyperSpecies.getInstance().getScoreboard().registerNewTeam(uuid.toString());
        scoreboardTeam.setColor(ChatColor.GRAY);
        scoreboardTeam.setPrefix((species != null ? species.prefix() : "§7None") + " §7| ");
        scoreboardTeam.addEntry(Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName());

        if (name != null) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) player.setDisplayName(name);
        }

    }

    /**
     * Get the character name provided by the player. This is not the username, but a custom
     * defined character name for roleplay.
     *
     * @return The player's character name.
     */
    public String name() {
        return name;
    }

    /**
     * Provide a new name for this player. This will not override the username, it will only
     * set a custom roleplay name.
     *
     * @param name The player's character name.
     */
    public void name(String name) {
        this.name = name;
    }

    public int experience() {
        return experience;
    }

    public void experience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return getLevelFromExperience(experience);
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return 20 + getLevel() * 3;
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
        return 20 + getLevel();
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

    public Team getTeam() {
        return Team.getTeam(team);
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean hasTeam() {
        return (Team.getTeam(team) != null);
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

    public boolean hasAbility(Ability ability) {
        return abilities.contains(ability);
    }

    public void addAbility(Ability ability) {
        if (!abilities.contains(ability)) abilities.add(ability);
    }

    public List<Ability> getActiveAbilities() {
        return activeAbilities;
    }

    public void addActiveAbility(Ability ability) {
        if (!activeAbilities.contains(ability)) activeAbilities.add(ability);
    }

    public void removeActiveAbility(Ability ability) {
        activeAbilities.remove(ability);
    }

    public boolean isSelectingAbility() {
        return selectingAbility;
    }

    public void setSelectingAbility(boolean selectingAbility) {
        this.selectingAbility = selectingAbility;
    }

    @SuppressWarnings("all")
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

    public int getAbilityExperience(Ability ability) {
        return abilityExperiences.getOrDefault(ability.name(), 0);
    }

    public void setAbilityExperience(Ability ability, int experience) {
        abilityExperiences.remove(ability.name());
        abilityExperiences.put(ability.name(), experience);
    }

    public int getAbilityLevel(Ability ability) {
        return getLevelFromExperience(getAbilityExperience(ability));
    }

    public void setAbilityLevel(Ability ability, int level) {
        setAbilityExperience(ability, getLevelThreshold(level));
    }

    public void addAbilityExperience(Ability ability, int experience) {
        int oldExperience = abilityExperiences.getOrDefault(ability.name(), 0);
        abilityExperiences.remove(ability.name());
        abilityExperiences.put(ability.name(), oldExperience + experience);
    }

    public double getDrunkenness() {
        return drunkenness;
    }

    public void setDrunkenness(double drunkenness) {
        this.drunkenness = drunkenness;
    }

    public boolean isDrunk() {
        return drunkenness > 0;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public void stun(int duration) {
        setStunned(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> setStunned(false), duration);
    }

    public ChatChannel getChannel() {
        return channel;
    }

    public void setChannel(ChatChannel channel) {
        this.channel = channel;
    }

    public void unlockAbility(Ability ability) {
        if (!hasAbility(ability)) {
            addAbility(ability);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage(Config.MESSAGE_PREFIX + "§7 You unlocked the ability§8: §b" + ability.name());
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100, 1f);
            }
        }
    }

    public int getMaxAbilityWeight() {
        return getLevel() * 2;
    }

    public int getAbilityWeight() {
        int weight = 0;
        for (Ability ability : activeAbilities) {
            weight += ability.weight();
        }
        return weight;
    }

    public boolean isKawaii() {
        return kawaii;
    }

    public void setKawaii(boolean kawaii) {
        this.kawaii = kawaii;
    }

    // STATIC METHODS

    public static HashMap<UUID, PlayerManager> getPlayers() {
        return players;
    }

}
