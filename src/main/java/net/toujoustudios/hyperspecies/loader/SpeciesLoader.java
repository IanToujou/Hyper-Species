package net.toujoustudios.hyperspecies.loader;

import net.toujoustudios.hyperspecies.data.ability.passive.*;
import net.toujoustudios.hyperspecies.data.element.Element;
import net.toujoustudios.hyperspecies.data.species.Species;
import net.toujoustudios.hyperspecies.data.species.SubSpecies;
import net.toujoustudios.hyperspecies.item.ItemList;
import net.toujoustudios.hyperspecies.item.ItemListUI;

import java.util.ArrayList;
import java.util.List;

public class SpeciesLoader {

    @SuppressWarnings("all")
    public static void initialize() {

        ArrayList<SubSpecies> demonSpecies = new ArrayList<>();
        demonSpecies.add(new SubSpecies("Hellspawn", null));
        demonSpecies.add(new SubSpecies("Arch Angel", null));

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
                new PassiveDemon(),
                List.of(Element.WATER, Element.FAIRY, Element.LIGHT)
        );

        Species.createSpecies(
                "Elf",
                "§2Elf",
                ItemListUI.ELF,
                elfSpecies,
                new PassiveElf(),
                List.of(Element.FIRE, Element.ELECTRO, Element.DARK)
        );

        Species.createSpecies(
                "Reptile",
                "§3Reptile",
                ItemListUI.REPTILE,
                reptileSpecies,
                new PassiveReptile(),
                List.of(Element.FAIRY, Element.PSYCHIC, Element.LIGHT)
        );

        Species.createSpecies(
                "Angel",
                "§eAngel",
                ItemListUI.ANGEL,
                angelSpecies,
                new PassiveAngel(),
                List.of(Element.FIRE, Element.EARTH, Element.DARK)
        );

        Species.createSpecies(
                "Human",
                "§dHuman",
                ItemListUI.HUMAN,
                humanSpecies,
                null,
                null
        );

        Species.createSpecies(
                "Aquatilia",
                "§bAqua",
                ItemListUI.AQUATILIA,
                aquatiliaSpecies,
                new PassiveAquatilia(),
                List.of(Element.EARTH, Element.ELECTRO, Element.AIR)
        );

        Species.createSpecies(
                "Feline",
                "§aFeline",
                ItemListUI.FELINE,
                felineSpecies,
                new PassiveFeline(),
                List.of(Element.WATER, Element.ELECTRO, Element.NORMAL)
        );

        Species.createSpecies(
                "Dwarf",
                "§6Dwarf",
                ItemListUI.DWARF,
                dwarfSpecies,
                new PassiveDwarf(),
                List.of(Element.FLORA, Element.FAIRY, Element.PSYCHIC)
        );

        Species.createSpecies(
                "Wolf",
                "§8Wolf",
                ItemListUI.WOLF,
                wolfSpecies,
                new PassiveWolf(),
                List.of(Element.FIRE, Element.AIR, Element.PSYCHIC)
        );

    }

}
