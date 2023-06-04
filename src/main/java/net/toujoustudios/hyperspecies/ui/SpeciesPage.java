package net.toujoustudios.hyperspecies.ui;

public enum SpeciesPage {

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

    SpeciesPage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
