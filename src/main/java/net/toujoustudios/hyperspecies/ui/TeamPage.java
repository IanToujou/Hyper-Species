package net.toujoustudios.hyperspecies.ui;

public enum TeamPage {

    MAIN(0),
    BROWSE(1);

    private final int index;

    TeamPage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
