package net.toujoustudios.hyperspecies.data.player;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.species.Species;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private static final HashMap<UUID, PlayerManager> players = new HashMap<>();
    private static final YamlConfiguration playerConfig = Config.getConfigFile("playerdata.yml");

    private final UUID uuid;
    private Species species;

    public PlayerManager(UUID uuid) {
        this.uuid = uuid;
        species = Species.getSpecies(playerConfig.getString("Data." + uuid + ".Species"));
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
        playerConfig.set("Player." + uuid + ".Species", species.getName());
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

    // GETTERS AND SETTERS

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    // CUSTOM METHODS

    // STATIC METHODS

    public static HashMap<UUID, PlayerManager> getPlayers() {
        return players;
    }

    public static YamlConfiguration getPlayerConfig() {
        return playerConfig;
    }

}
