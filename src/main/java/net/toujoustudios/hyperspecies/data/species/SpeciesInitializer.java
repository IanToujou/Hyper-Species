package net.toujoustudios.hyperspecies.data.species;

import net.toujoustudios.hyperspecies.data.ability.AbilityDemonicRage;
import net.toujoustudios.hyperspecies.data.ability.AbilityEnhancingFlame;
import net.toujoustudios.hyperspecies.data.ability.AbilityHellblight;
import net.toujoustudios.hyperspecies.data.ability.AbilityMeteorStrike;
import net.toujoustudios.hyperspecies.item.ItemList;

import java.util.ArrayList;
import java.util.List;

public class SpeciesInitializer {

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
        dwarfSpecies.add(new SubSpecies("Miner", null));
        dwarfSpecies.add(new SubSpecies("Cartographer", null));

        ArrayList<SubSpecies> wolfSpecies = new ArrayList<>();
        wolfSpecies.add(new SubSpecies("Snow Wolf", null));
        wolfSpecies.add(new SubSpecies("Forest Wolf", null));

        Species.createSpecies("Demon", ItemList.DEMON, List.of(
                new AbilityHellblight(),
                new AbilityMeteorStrike(),
                new AbilityEnhancingFlame(),
                new AbilityDemonicRage()
        ), demonSpecies);
        Species.createSpecies("Elf", ItemList.ELF, null, elfSpecies);
        Species.createSpecies("Reptile", ItemList.REPTILE, null, reptileSpecies);
        Species.createSpecies("Angel", ItemList.ANGEL, null, angelSpecies);
        Species.createSpecies("Human", ItemList.HUMAN, null, humanSpecies);
        Species.createSpecies("Aquatilia", ItemList.AQUATILIA, null, aquatiliaSpecies);
        Species.createSpecies("Feline", ItemList.FELINE, null, felineSpecies);
        Species.createSpecies("Dwarf", ItemList.DWARF, null, dwarfSpecies);
        Species.createSpecies("Wolf", ItemList.WOLF, null, wolfSpecies);
    }

}
