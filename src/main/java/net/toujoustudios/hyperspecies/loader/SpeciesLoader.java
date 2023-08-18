package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.ability.passive.*;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import net.toujoustudios.hyperspecies.species.Species;
import net.toujoustudios.hyperspecies.species.SubSpecies;

import java.util.ArrayList;

public class SpeciesLoader {

    @SuppressWarnings("all")
    public static void initialize() {

        ArrayList<SubSpecies> demonSpecies = new ArrayList<>();
        demonSpecies.add(new SubSpecies("Hellspawn"));
        demonSpecies.add(new SubSpecies("Fallen Angel"));

        ArrayList<SubSpecies> elfSpecies = new ArrayList<>();
        elfSpecies.add(new SubSpecies("Dryad"));
        elfSpecies.add(new SubSpecies("Guardian"));

        ArrayList<SubSpecies> reptileSpecies = new ArrayList<>();
        reptileSpecies.add(new SubSpecies("Lizard"));
        reptileSpecies.add(new SubSpecies("Snake"));

        ArrayList<SubSpecies> angelSpecies = new ArrayList<>();
        angelSpecies.add(new SubSpecies("Arch Angel"));
        angelSpecies.add(new SubSpecies("Demi God"));

        ArrayList<SubSpecies> humanSpecies = new ArrayList<>();
        humanSpecies.add(new SubSpecies("Warrior"));
        humanSpecies.add(new SubSpecies("Mage"));

        ArrayList<SubSpecies> aquatiliaSpecies = new ArrayList<>();
        aquatiliaSpecies.add(new SubSpecies("Hydra"));
        aquatiliaSpecies.add(new SubSpecies("Siren"));

        ArrayList<SubSpecies> felineSpecies = new ArrayList<>();
        felineSpecies.add(new SubSpecies("Leopard"));
        felineSpecies.add(new SubSpecies("Forest Cat"));

        ArrayList<SubSpecies> dwarfSpecies = new ArrayList<>();
        dwarfSpecies.add(new SubSpecies("Blacksmith"));
        dwarfSpecies.add(new SubSpecies("Cartographer"));

        ArrayList<SubSpecies> wolfSpecies = new ArrayList<>();
        wolfSpecies.add(new SubSpecies("Snow Wolf"));
        wolfSpecies.add(new SubSpecies("Forest Wolf"));

        Species.createSpecies(
                "Demon",
                "§cDemon",
                ItemListUI.DEMON,
                demonSpecies,
                new PassiveDemon()
        );

        Species.createSpecies(
                "Elf",
                "§2Elf",
                ItemListUI.ELF,
                elfSpecies,
                new PassiveElf()
        );

        Species.createSpecies(
                "Reptile",
                "§3Reptile",
                ItemListUI.REPTILE,
                reptileSpecies,
                new PassiveReptile()
        );

        Species.createSpecies(
                "Angel",
                "§eAngel",
                ItemListUI.ANGEL,
                angelSpecies,
                new PassiveAngel()
        );

        Species.createSpecies(
                "Human",
                "§dHuman",
                ItemListUI.HUMAN,
                humanSpecies,
                null
        );

        Species.createSpecies(
                "Aquatilia",
                "§bAqua",
                ItemListUI.AQUATILIA,
                aquatiliaSpecies,
                new PassiveAquatilia()
        );

        Species.createSpecies(
                "Feline",
                "§aFeline",
                ItemListUI.FELINE,
                felineSpecies,
                new PassiveFeline()
        );

        Species.createSpecies(
                "Dwarf",
                "§6Dwarf",
                ItemListUI.DWARF,
                dwarfSpecies,
                new PassiveDwarf()
        );

        Species.createSpecies(
                "Wolf",
                "§8Wolf",
                ItemListUI.WOLF,
                wolfSpecies,
                new PassiveWolf()
        );

    }

}
