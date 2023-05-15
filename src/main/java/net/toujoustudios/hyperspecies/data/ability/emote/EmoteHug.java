package net.toujoustudios.hyperspecies.data.ability.emote;

import net.toujoustudios.hyperspecies.config.Config;
import org.bukkit.entity.Player;

public class EmoteHug extends Emote {

    public EmoteHug() {
        super("hug", true);
    }

    @Override
    public boolean execute(Player player, Player target) {

        player.sendMessage("You hugged " + target.getName());
        target.sendMessage("You got hugged by " + player.getName());

        return true;

    }

}
