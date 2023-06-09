package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.team.Team;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamLoader {

    private static final YamlConfiguration teamConfig = Config.getConfigFile("teams.yml");

    public static void initialize() {

        List<String> teams = teamConfig.getStringList("List");

        teams.forEach(team -> {
            String color = teamConfig.getString("Data." + team + ".Color");
            String owner = teamConfig.getString("Data." + team + ".Owner");
            List<UUID> members = new ArrayList<>();
            if(teamConfig.getStringList("Data." + team + ".Members").size() > 0)
                teamConfig.getStringList("Data." + team + ".Members").forEach(member -> members.add(UUID.fromString(member)));
            assert owner != null;
            Team.createTeam(team, color, UUID.fromString(owner), members);
        });

    }

}
