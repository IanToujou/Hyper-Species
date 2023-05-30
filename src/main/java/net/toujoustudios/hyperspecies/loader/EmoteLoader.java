package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.emote.*;

public class EmoteLoader {

    public static void initialize() {
        Emote.createEmote(new EmoteHug());
        Emote.createEmote(new EmoteBlush());
        Emote.createEmote(new EmoteCry());
        Emote.createEmote(new EmoteCuddle());
        Emote.createEmote(new EmoteKiss());
        Emote.createEmote(new EmoteNom());
        Emote.createEmote(new EmoteLick());
        Emote.createEmote(new EmotePurr());
        Emote.createEmote(new EmoteTickle());
    }

}
