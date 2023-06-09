package net.toujoustudios.hyperspecies.data.emote;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Collection;

public class EmoteBlush extends Emote {

    public EmoteBlush() {
        super("blush", false);
    }

    @Override
    public boolean execute(Player player, Player target) {

        player.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §7You are blushing§8. §d>///<");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 15*15;
        players.forEach(all -> {
            if(all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                if(all != player) all.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §e" + player.getName() + "§7 is blushing§8. §d>///<");
                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);
            }
        });

        return true;

    }

}
