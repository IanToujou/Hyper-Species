package net.toujoustudios.hyperspecies.data.emote;

import net.toujoustudios.hyperspecies.config.Config;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class EmoteLick extends Emote {

    public EmoteLick() {
        super("lick", true);
    }

    @Override
    public boolean execute(Player player, Player target) {

        player.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §7You are licking §e" + target.getName() + " §a:P");
        target.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §7You got licked by §e" + player.getName() + " §a:P");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);

        return true;

    }
}
