package net.toujoustudios.hyperspecies.ui;

public enum TeamPage {

    MAIN(0),
    BROWSE(1),
    SETTINGS(2),
    SETTINGS_ADMIN(3),
    SETTINGS_STATUS(4),
    LEAVE_CONFIRM(5);

    private final int index;

    TeamPage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
