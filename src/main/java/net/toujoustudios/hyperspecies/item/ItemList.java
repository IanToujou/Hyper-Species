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
    public static ItemStack ALCOHOL_ANIME_BODY_FLUIDS = new ItemStack(Material.POTION);

    @SuppressWarnings("all")
    public static void initialize() {

        PotionMeta alcoholBeerMeta = (PotionMeta) ALCOHOL_BEER.getItemMeta();
        alcoholBeerMeta.setColor(Color.YELLOW);
        alcoholBeerMeta.setDisplayName("§6Beer");
        alcoholBeerMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_BEER.setItemMeta(alcoholBeerMeta);

        PotionMeta alcoholRumMeta = (PotionMeta) ALCOHOL_RUM.getItemMeta();
        alcoholRumMeta.setColor(Color.ORANGE);
        alcoholRumMeta.setDisplayName("§6Rum");
        alcoholRumMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_RUM.setItemMeta(alcoholRumMeta);

        PotionMeta alcoholRedWineMeta = (PotionMeta) ALCOHOL_RED_WINE.getItemMeta();
        alcoholRedWineMeta.setColor(Color.ORANGE);
        alcoholRedWineMeta.setDisplayName("§6Red Wine");
        alcoholRedWineMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_RED_WINE.setItemMeta(alcoholRedWineMeta);

        PotionMeta alcoholWhiteWineMeta = (PotionMeta) ALCOHOL_WHITE_WINE.getItemMeta();
        alcoholWhiteWineMeta.setColor(Color.WHITE);
        alcoholWhiteWineMeta.setDisplayName("§6White Wine");
        alcoholWhiteWineMeta.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_WHITE_WINE.setItemMeta(alcoholWhiteWineMeta);

        PotionMeta alcoholAnimeBodyFluids = (PotionMeta) ALCOHOL_ANIME_BODY_FLUIDS.getItemMeta();
        alcoholAnimeBodyFluids.setColor(Color.PURPLE);
        alcoholAnimeBodyFluids.setDisplayName("§6Anime Girl Body Fluids");
        alcoholAnimeBodyFluids.setBasePotionData(new PotionData(PotionType.WATER));
        ALCOHOL_ANIME_BODY_FLUIDS.setItemMeta(alcoholAnimeBodyFluids);

    }

}
