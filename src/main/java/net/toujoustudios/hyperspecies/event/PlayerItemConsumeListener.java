package net.toujoustudios.hyperspecies.event;

import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PlayerItemConsumeListener implements Listener {

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if(event.getItem().getType() == Material.MILK_BUCKET) playerManager.setKawaii(false);

        if(playerManager.getSpecies() == null) return;

        ItemMeta itemMeta = event.getItem().getItemMeta();
        if(itemMeta != null) {
            if(itemMeta.getDisplayName().equals("§6Beer")) {
                playerManager.setDrunkenness(playerManager.getDrunkenness() + 0.2);
                int nauseaDuration = (int) Math.round(playerManager.getDrunkenness() * 10);
                int blindDuration = (int) Math.round(playerManager.getDrunkenness() * 2);
                if (nauseaDuration > 0) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * nauseaDuration, 0, false, false, true));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                }
                if (playerManager.getDrunkenness() > 3) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * blindDuration, 0, false, false, true));
                return;
            } else if(itemMeta.getDisplayName().equals("§6Rum")) {
                playerManager.setDrunkenness(playerManager.getDrunkenness() + 0.6);
                int nauseaDuration = (int) Math.round(playerManager.getDrunkenness() * 10);
                int blindDuration = (int) Math.round(playerManager.getDrunkenness() * 2);
                if (nauseaDuration > 0) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * nauseaDuration, 0, false, false, true));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                }
                if (playerManager.getDrunkenness() > 3) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * blindDuration, 0, false, false, true));
                return;
            } else if(itemMeta.getDisplayName().equals("§6Red Wine")) {
                playerManager.setDrunkenness(playerManager.getDrunkenness() + 0.5);
                int nauseaDuration = (int) Math.round(playerManager.getDrunkenness() * 10);
                int blindDuration = (int) Math.round(playerManager.getDrunkenness() * 2);
                if (nauseaDuration > 0) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * nauseaDuration, 0, false, false, true));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                }
                if (playerManager.getDrunkenness() > 3) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * blindDuration, 0, false, false, true));
                return;
            } else if(itemMeta.getDisplayName().equals("§6White Wine")) {
                playerManager.setDrunkenness(playerManager.getDrunkenness() + 0.5);
                int nauseaDuration = (int) Math.round(playerManager.getDrunkenness() * 10);
                int blindDuration = (int) Math.round(playerManager.getDrunkenness() * 2);
                if (nauseaDuration > 0) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * nauseaDuration, 0, false, false, true));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                }
                if (playerManager.getDrunkenness() > 3) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * blindDuration, 0, false, false, true));
                return;
            } else if(itemMeta.getDisplayName().equals("§6Anime Girl Body Fluids")) {
                playerManager.setDrunkenness(playerManager.getDrunkenness() + 1);
                Bukkit.broadcastMessage("§e" + player.getName() + " §7is turning into an anime girl§8...");
                player.sendMessage("§7Pssht§8... §7To revert this, drink milk§8.");
                playerManager.setKawaii(true);
                int nauseaDuration = (int) Math.round(playerManager.getDrunkenness() * 10);
                int blindDuration = (int) Math.round(playerManager.getDrunkenness() * 2);
                if (nauseaDuration > 0) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * nauseaDuration, 0, false, false, true));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                }
                if (playerManager.getDrunkenness() > 3) player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * blindDuration, 0, false, false, true));
                return;
            }
        }

        List<Material> exceptions = List.of(
                Material.GOLDEN_APPLE,
                Material.ENCHANTED_GOLDEN_APPLE,
                Material.POTION,
                Material.MILK_BUCKET
        );

        List<Material> meat = List.of(
                Material.CHICKEN,
                Material.COOKED_CHICKEN,
                Material.BEEF,
                Material.COOKED_BEEF,
                Material.PORKCHOP,
                Material.COOKED_PORKCHOP,
                Material.RABBIT,
                Material.COOKED_RABBIT,
                Material.MUTTON,
                Material.COOKED_MUTTON,
                Material.ROTTEN_FLESH,
                Material.COD,
                Material.COOKED_COD,
                Material.SALMON,
                Material.COOKED_SALMON,
                Material.TROPICAL_FISH,
                Material.PUFFERFISH
        );

        Material material = event.getItem().getType();

        if(exceptions.contains(material)) {
            return;
        }

        if(playerManager.getSpecies().getName().equals("Elf")) {

            if(meat.contains(material)) {

                player.damage(5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 0, false, false, true));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 1.5f);
                event.setCancelled(true);

            }

        } else if(playerManager.getSpecies().getName().equals("Feline") || playerManager.getSpecies().getName().equals("Wolf")) {

            if(!meat.contains(material)) {

                player.damage(5);
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 0, false, false, true));
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, SoundCategory.MASTER, 1, 0.5f);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, SoundCategory.MASTER, 1, 1f);
                event.setCancelled(true);

            }

        }

    }

}
