package net.toujoustudios.hyperspecies.team;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.ui.TeamUI;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

public class Team {

    private static final HashMap<String, Team> teams = new HashMap<>();
    private static final YamlConfiguration teamConfig = Config.getConfigFile("data/teams.yml");

    private final ArrayList<String> oldNames = new ArrayList<>();
    private String name;
    private String description;
    private String color;
    private UUID owner;
    private TeamStatus status;
    private final ArrayList<UUID> members;

    public Team(String name, String description, String color, UUID owner, TeamStatus status, ArrayList<UUID> members) {
        oldNames.add(name);
        this.name = name;
        this.description = description;
        this.color = color;
        this.owner = owner;
        this.status = status;
        this.members = members;
    }

    public void save() {

        oldNames.forEach(oldName -> teamConfig.set("Data." + oldName, null));

        Config.saveToFile(teamConfig, "data/teams.yml");

        if (owner == null && !members.isEmpty()) {
            int random = new Random().nextInt(members.size());
            owner = members.get(random);
            members.remove(random);
        }

        if (owner == null && members.isEmpty()) {
            teamConfig.set("Data." + name, null);
            Config.saveToFile(teamConfig, "data/teams.yml");
        } else {
            teamConfig.set("Data." + name + ".Color", color);
            teamConfig.set("Data." + name + ".Description", description);
            teamConfig.set("Data." + name + ".Owner", (owner != null ? owner.toString() : null));
            teamConfig.set("Data." + name + ".Status", status.getName());
            ArrayList<String> memberList = new ArrayList<>();
            members.forEach(member -> memberList.add(member.toString()));
            teamConfig.set("Data." + name + ".Members", memberList);
            Config.saveToFile(teamConfig, "data/teams.yml");
        }

    }

    public static void unloadAll() {
        saveAll();
        teams.clear();
    }

    public static void saveAll() {
        if (teams == null || teams.isEmpty()) {
            teamConfig.set("List", null);
            Config.saveToFile(teamConfig, "data/teams.yml");
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Team> entry : teams.entrySet()) {
            teams.get(entry.getKey()).save();
            if (teams.get(entry.getKey()).getOwner() != null) list.add(entry.getKey());
        }
        teamConfig.set("List", list);
        Config.saveToFile(teamConfig, "data/teams.yml");
    }

    public static Team getTeam(String name) {
        if (teams.containsKey(name)) return teams.get(name);
        return null;
    }

    public static void createTeam(String name, String description, String color, UUID owner, TeamStatus status, ArrayList<UUID> members) {
        teams.put(name, new Team(name, description, color, owner, status, (members != null ? members : new ArrayList<>())));
        TeamUI.refresh();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        oldNames.add(this.name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    public static HashMap<String, Team> getTeams() {
        return teams;
    }

}
