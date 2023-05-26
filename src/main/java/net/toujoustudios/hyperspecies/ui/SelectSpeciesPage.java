package net.toujoustudios.hyperspecies.ui;

public enum SelectSpeciesPage {

    MAIN(0),
    DEMON(1),
    ELF(2),
    REPTILE(3),
    ANGEL(4),
    HUMAN(5),
    AQUATILIA(6),
    FELINE(7),
    DWARF(8),
    WOLF(9);

    private final int index;

    SelectSpeciesPage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
