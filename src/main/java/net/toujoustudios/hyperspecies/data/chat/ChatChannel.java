package net.toujoustudios.hyperspecies.data.chat;

public enum ChatChannel {

    LOCAL("Local", "§8⛳", "§7"),
    GLOBAL("Local", "§9\uD83C\uDF0D", "§b"),
    SUPPORT("Local", "§5\uD83C\uDF10", "§d"),
    TEAM("Team", "§2\uD83D\uDCBE", "§a"),
    ADMIN("Admin", "§4\uD83D\uDEE1", "§c");

    private final String name;
    private final String emoji;
    private final String chatColor;

    ChatChannel(String name, String emoji, String chatColor) {
        this.name = name;
        this.emoji = emoji;
        this.chatColor = chatColor;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getFullName() {
        return emoji + " " + name;
    }

    public String getChatColor() {
        return chatColor;
    }

}
