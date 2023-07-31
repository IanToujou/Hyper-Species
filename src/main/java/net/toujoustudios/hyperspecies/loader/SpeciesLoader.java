package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.ability.passive.*;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.data.species.SubSpecies;
import net.toujoustudios.hyperspecies.item.ItemListUI;

import java.util.ArrayList;

public class SpeciesLoader {

    @SuppressWarnings("all")
    public static void initialize() {

        ArrayList<SubSpecies> demonSpecies = new ArrayList<>();
        demonSpecies.add(new SubSpecies("Hellspawn", null));
        demonSpecies.add(new SubSpecies("Fallen Angel", null));

        ArrayList<SubSpecies> elfSpecies = new ArrayList<>();
        elfSpecies.add(new SubSpecies("Dryad", null));
        elfSpecies.add(new SubSpecies("Guardian", null));

        ArrayList<SubSpecies> reptileSpecies = new ArrayList<>();
        reptileSpecies.add(new SubSpecies("Lizard", null));
        reptileSpecies.add(new SubSpecies("Snake", null));

        ArrayList<SubSpecies> angelSpecies = new ArrayList<>();
        angelSpecies.add(new SubSpecies("Arch Angel", null));
        angelSpecies.add(new SubSpecies("Demi God", null));

        ArrayList<SubSpecies> humanSpecies = new ArrayList<>();
        humanSpecies.add(new SubSpecies("Warrior", null));
        humanSpecies.add(new SubSpecies("Mage", null));

        ArrayList<SubSpecies> aquatiliaSpecies = new ArrayList<>();
        aquatiliaSpecies.add(new SubSpecies("Hydra", null));
        aquatiliaSpecies.add(new SubSpecies("Siren", null));

        ArrayList<SubSpecies> felineSpecies = new ArrayList<>();
        felineSpecies.add(new SubSpecies("Leopard", null));
        felineSpecies.add(new SubSpecies("Forest Cat", null));

        ArrayList<SubSpecies> dwarfSpecies = new ArrayList<>();
        dwarfSpecies.add(new SubSpecies("Blacksmith", null));
        dwarfSpecies.add(new SubSpecies("Cartographer", null));

        ArrayList<SubSpecies> wolfSpecies = new ArrayList<>();
        wolfSpecies.add(new SubSpecies("Snow Wolf", null));
        wolfSpecies.add(new SubSpecies("Forest Wolf", null));

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
