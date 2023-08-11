package net.toujoustudios.hyperspecies.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class ItemList {

    public static ItemStack BEER = new ItemStack(Material.POTION);
    public static ItemStack RUM = new ItemStack(Material.POTION);
    public static ItemStack RED_WINE = new ItemStack(Material.POTION);
    public static ItemStack WHITE_WINE = new ItemStack(Material.POTION);
    public static ItemStack MEAD = new ItemStack(Material.POTION);
    public static ItemStack MOLOTOV_COCKTAIL = new ItemStack(Material.SPLASH_POTION);
    public static ItemStack ANIME_GIRL_FLUIDS = new ItemStack(Material.POTION);

    @SuppressWarnings("all")
    public static void initialize() {

        PotionMeta beerMeta = (PotionMeta) BEER.getItemMeta();
        beerMeta.setColor(Color.YELLOW);
        beerMeta.setDisplayName("§eBeer");
        beerMeta.setBasePotionData(new PotionData(PotionType.WATER));
        BEER.setItemMeta(beerMeta);

        PotionMeta rumMeta = (PotionMeta) RUM.getItemMeta();
        rumMeta.setColor(Color.ORANGE);
        rumMeta.setDisplayName("§6Rum");
        rumMeta.setBasePotionData(new PotionData(PotionType.WATER));
        RUM.setItemMeta(rumMeta);

        PotionMeta redWineMeta = (PotionMeta) RED_WINE.getItemMeta();
        redWineMeta.setColor(Color.RED);
        redWineMeta.setDisplayName("§cRed Wine");
        redWineMeta.setBasePotionData(new PotionData(PotionType.WATER));
        RED_WINE.setItemMeta(redWineMeta);

        PotionMeta whiteWineMeta = (PotionMeta) WHITE_WINE.getItemMeta();
        whiteWineMeta.setColor(Color.WHITE);
        whiteWineMeta.setDisplayName("§fWhite Wine");
        whiteWineMeta.setBasePotionData(new PotionData(PotionType.WATER));
        WHITE_WINE.setItemMeta(whiteWineMeta);

        PotionMeta meadMeta = (PotionMeta) MEAD.getItemMeta();
        meadMeta.setColor(Color.ORANGE);
        meadMeta.setDisplayName("§6Mead");
        meadMeta.setBasePotionData(new PotionData(PotionType.WATER));
        MEAD.setItemMeta(meadMeta);

        PotionMeta molotovCocktailMeta = (PotionMeta) MOLOTOV_COCKTAIL.getItemMeta();
        molotovCocktailMeta.setColor(Color.ORANGE);
        molotovCocktailMeta.setDisplayName("§fMolotov Cocktail");
        molotovCocktailMeta.setBasePotionData(new PotionData(PotionType.WATER));
        MOLOTOV_COCKTAIL.setItemMeta(molotovCocktailMeta);

        PotionMeta animeBodyFluidsMeta = (PotionMeta) ANIME_GIRL_FLUIDS.getItemMeta();
        animeBodyFluidsMeta.setColor(Color.PURPLE);
        animeBodyFluidsMeta.setDisplayName("§5Anime Girl Fluids");
        animeBodyFluidsMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ANIME_GIRL_FLUIDS.setItemMeta(animeBodyFluidsMeta);

    }

}
