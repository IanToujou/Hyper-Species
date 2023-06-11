package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.team.Team;
import net.toujoustudios.hyperspecies.data.team.TeamStatus;
import net.toujoustudios.hyperspecies.log.LogLevel;
import net.toujoustudios.hyperspecies.log.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TeamLoader {

    private static final YamlConfiguration teamConfig = Config.getConfigFile("teams.yml");

    public static void initialize() {

        List<String> teams = teamConfig.getStringList("List");
        ArrayList<String> teamsToEdit = new ArrayList<>();

        teams.forEach(team -> {

            String color = teamConfig.getString("Data." + team + ".Color");
            String description = teamConfig.getString("Data." + team + ".Description");
            String owner = teamConfig.getString("Data." + team + ".Owner");
            String statusString = teamConfig.getString("Data." + team + ".Status");
            TeamStatus status = TeamStatus.INVITE;
            if(statusString != null) status = TeamStatus.valueOf(statusString.toUpperCase());
            List<UUID> members = new ArrayList<>();
            if(teamConfig.getStringList("Data." + team + ".Members").size() > 0)
                teamConfig.getStringList("Data." + team + ".Members").forEach(member -> members.add(UUID.fromString(member)));
            Team.createTeam(team, description, color, (owner != null ? UUID.fromString(owner) : null), status, members);

            if(owner == null) {
                teamsToEdit.add(team);
            }

        });

        teamsToEdit.forEach(teamName -> {
            Team team = Team.getTeam(teamName);
            if(team != null) {
                if(team.getMembers().size() == 0) {
                    teamConfig.set("Data." + team.getName(), null);
                    Config.saveToFile(teamConfig, "teams.yml");
                    Team.getTeams().remove(teamName);
                    Logger.log(LogLevel.DEBUG, "Deleting team " + team.getName() + " because it is empty.");
                } else {
                    int random = new Random().nextInt(team.getMembers().size());
                    team.setOwner(team.getMembers().get(random));
                    Logger.log(LogLevel.DEBUG, "Fixing team " + team.getName() + ". Setting " + team.getMembers().get(random) + " as new owner.");
                    team.getMembers().remove(random);
                }
            }
        });

    }

}
