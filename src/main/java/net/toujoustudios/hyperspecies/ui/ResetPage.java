package net.toujoustudios.hyperspecies.ui;

public enum ResetPage {

    MAIN(0),
    CONFIRM(1);

    private final int index;

    ResetPage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
