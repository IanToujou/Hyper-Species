package net.toujoustudios.hyperspecies.data.ability.emote;

import org.bukkit.entity.Player;

public class EmoteBlush extends Emote {

    public EmoteBlush() {
        super("blush", false);
    }

    @Override
    public boolean execute(Player player, Player target) {

        player.sendMessage("blush");

        return true;

    }

}
