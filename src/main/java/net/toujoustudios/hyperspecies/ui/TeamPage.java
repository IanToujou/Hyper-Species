package net.toujoustudios.hyperspecies.ui;

public enum TeamPage {

    MAIN(0),
    SETTINGS(1),
    SETTINGS_ADMIN(2),
    SETTINGS_STATUS(3),
    LEAVE_CONFIRM(4);

    private final int index;

    TeamPage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
