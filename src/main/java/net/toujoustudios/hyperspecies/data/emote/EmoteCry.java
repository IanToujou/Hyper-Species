package net.toujoustudios.hyperspecies.data.emote;

import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Collection;

public class EmoteCry extends Emote {


    public EmoteCry() {
        super("cry", false);
    }

    @Override
    public boolean execute(Player player, Player target) {

        player.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §7You are crying§8. §b:'(");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);

        Collection<? extends Player> players = HyperSpecies.getInstance().getServer().getOnlinePlayers();
        double radiusSquared = 15 * 15;
        players.forEach(all -> {
            if (all.getLocation().distanceSquared(player.getLocation()) <= radiusSquared) {
                if (all != player)
                    all.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §e" + player.getName() + "§7 is crying§8. §b:'(");
                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);
            }
        });

        return true;

    }

}
