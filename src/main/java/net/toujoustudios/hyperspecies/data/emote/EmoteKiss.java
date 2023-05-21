package net.toujoustudios.hyperspecies.data.emote;

import net.toujoustudios.hyperspecies.config.Config;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class EmoteKiss extends Emote {
    public EmoteKiss() {
        super("kiss", true);
    }

    @Override
    public boolean execute(Player player, Player target) {

        player.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §7You are kissing §e" + target.getName() + " §c❤");
        target.sendMessage(Config.MESSAGE_PREFIX_ROLEPLAY + " §7You got kissed by §e" + player.getName() + " §c❤");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 1.8f);

        return true;

    }
}
