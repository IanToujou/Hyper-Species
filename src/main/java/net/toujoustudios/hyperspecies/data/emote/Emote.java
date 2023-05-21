package net.toujoustudios.hyperspecies.data.emote;

import org.bukkit.entity.Player;

import java.util.HashMap;

public abstract class Emote {

    private static final HashMap<String, Emote> emotes = new HashMap<>();

    private final String name;
    private final boolean targeting;

    public Emote(String name, boolean targeting) {
        this.name = name;
        this.targeting = targeting;
        emotes.put(name, this);
    }

    public static void initialize() {
        createEmote(new EmoteHug());
        createEmote(new EmoteBlush());
        createEmote(new EmoteCry());
        createEmote(new EmoteCuddle());
        createEmote(new EmoteKiss());
        createEmote(new EmoteNom());
        createEmote(new EmoteLick());
        createEmote(new EmotePurr());
        createEmote(new EmoteTickle());
    }

    public static void createEmote(Emote emote) {
        emotes.put(emote.name, emote);
    }

    public abstract boolean execute(Player player, Player target);

    public String getName() {
        return name;
    }

    public boolean isTargeting() {
        return targeting;
    }

    public static HashMap<String, Emote> getEmotes() {
        return emotes;
    }

    public static Emote getEmoteByName(String name) {
        return emotes.getOrDefault(name, null);
    }

}
