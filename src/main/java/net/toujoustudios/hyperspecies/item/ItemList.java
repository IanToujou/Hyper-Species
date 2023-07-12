package net.toujoustudios.hyperspecies.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class ItemList {

    public static ItemStack ALCOHOL_BEER = new ItemStack(Material.POTION);
    public static ItemStack ALCOHOL_RUM = new ItemStack(Material.POTION);
    public static ItemStack ALCOHOL_RED_WINE = new ItemStack(Material.POTION);
    public static ItemStack ALCOHOL_WHITE_WINE = new ItemStack(Material.POTION);
    public static ItemStack ALCOHOL_ANIME_GIRL_FLUIDS = new ItemStack(Material.POTION);
    public static ItemStack MOLOTOV_COCKTAIL = new ItemStack(Material.SPLASH_POTION);

    @SuppressWarnings("all")
    public static void initialize() {

        PotionMeta alcoholBeerMeta = (PotionMeta) ALCOHOL_BEER.getItemMeta();
        alcoholBeerMeta.setColor(Color.YELLOW);
        alcoholBeerMeta.setDisplayName("§eBeer");
        alcoholBeerMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_BEER.setItemMeta(alcoholBeerMeta);

        PotionMeta alcoholRumMeta = (PotionMeta) ALCOHOL_RUM.getItemMeta();
        alcoholRumMeta.setColor(Color.ORANGE);
        alcoholRumMeta.setDisplayName("§6Rum");
        alcoholRumMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_RUM.setItemMeta(alcoholRumMeta);

        PotionMeta alcoholRedWineMeta = (PotionMeta) ALCOHOL_RED_WINE.getItemMeta();
        alcoholRedWineMeta.setColor(Color.RED);
        alcoholRedWineMeta.setDisplayName("§cRed Wine");
        alcoholRedWineMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_RED_WINE.setItemMeta(alcoholRedWineMeta);

        PotionMeta alcoholWhiteWineMeta = (PotionMeta) ALCOHOL_WHITE_WINE.getItemMeta();
        alcoholWhiteWineMeta.setColor(Color.WHITE);
        alcoholWhiteWineMeta.setDisplayName("§fWhite Wine");
        alcoholWhiteWineMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_WHITE_WINE.setItemMeta(alcoholWhiteWineMeta);

        PotionMeta alcoholAnimeBodyFluids = (PotionMeta) ALCOHOL_ANIME_GIRL_FLUIDS.getItemMeta();
        alcoholAnimeBodyFluids.setColor(Color.PURPLE);
        alcoholAnimeBodyFluids.setDisplayName("§5Anime Girl Fluids");
        alcoholAnimeBodyFluids.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_ANIME_GIRL_FLUIDS.setItemMeta(alcoholAnimeBodyFluids);

        PotionMeta molotovCocktailMeta = (PotionMeta) MOLOTOV_COCKTAIL.getItemMeta();
        molotovCocktailMeta.setColor(Color.ORANGE);
        molotovCocktailMeta.setDisplayName("§fMolotov Cocktail");
        molotovCocktailMeta.setBasePotionData(new PotionData(PotionType.WATER));
        MOLOTOV_COCKTAIL.setItemMeta(molotovCocktailMeta);

    }

}
