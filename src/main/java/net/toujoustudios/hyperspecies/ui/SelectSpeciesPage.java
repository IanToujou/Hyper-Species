package net.toujoustudios.hyperspecies.ui;

public enum SelectSpeciesPage {

    MAIN(0),
    DEMON(1), DEMON_ABILITIES(2),
    ELF(3), ELF_ABILITIES(4),
    REPTILE(5), REPTILE_ABILITIES(6),
    ANGEL(7), ANGEL_ABILITIES(8),
    HUMAN(9), HUMAN_ABILITIES(10),
    AQUATILIA(11), AQUATILIA_ABILITIES(12),
    FELINE(13), FELINE_ABILITIES(14),
    DWARF(15), DWARF_ABILITIES(16),
    WOLF(17), WOLF_ABILITIES(18);

    private final int index;

    SelectSpeciesPage(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
