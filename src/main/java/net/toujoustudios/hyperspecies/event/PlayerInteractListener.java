package net.toujoustudios.hyperspecies.event;

import net.kyori.adventure.text.Component;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.data.ability.active.Ability;
import net.toujoustudios.hyperspecies.data.ability.tree.AbilityTree;
import net.toujoustudios.hyperspecies.data.player.PlayerManager;
import net.toujoustudios.hyperspecies.item.ItemListUI;
import net.toujoustudios.hyperspecies.main.HyperSpecies;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    private final ArrayList<UUID> cooldownPlayers = new ArrayList<>();

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);
        Action action = event.getAction();

        if (event.getItem() != null) {
            if (event.getItem().getItemMeta() != null) {
                if (event.getItem().getItemMeta().getDisplayName().equals("§fMolotov Cocktail")) {
                    event.setCancelled(true);
                    event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
                    ThrownPotion potion = player.launchProjectile(ThrownPotion.class);
                    potion.setCustomName("Molotov Cocktail");
                    potion.setCustomNameVisible(false);
                    potion.setVelocity(potion.getVelocity().normalize().multiply(1.2));
                }
            }
        }

        if (playerManager.getSpecies() == null) return;

        if (playerManager.isSelectingAbility()) {

            event.setCancelled(true);
            if (cooldownPlayers.contains(player.getUniqueId())) return;
            if (player.getItemInHand().getItemMeta() != null) {
                ItemStack item = player.getItemInHand();
                if (item.getItemMeta().getDisplayName().contains("§cCancel")) {
                    player.sendTitle("", "§cCancelled", 5, 10, 5);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.6f);
                } else if (item.getItemMeta().getDisplayName().contains("§a") || item.getItemMeta().getDisplayName().contains("§c")) {

                    if (item.getItemMeta().getLore() == null) return;
                    if (item.getItemMeta().getLore().size() < 1) return;
                    String abilityName = item.getItemMeta().getLore().get(0).substring(2);
                    Ability ability = Ability.getAbility(abilityName);

                    if (ability == null) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
                        player.sendTitle("", "§cFailed", 5, 10, 5);
                        return;
                    }

                    if (playerManager.getCooldownAbilities().contains(ability)) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
                        player.sendTitle("", "§cCooling Down", 5, 10, 5);
                        return;
                    }

                    if (playerManager.getMana() < ability.getManaCost()) {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
                        player.sendTitle("", "§cNo Mana", 5, 10, 5);
                        return;
                    }

                    if (playerManager.useAbility(ability)) {
                        player.sendTitle("", "§a" + ability.getName(), 5, 10, 5);
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 0.4f);
                        player.sendTitle("", "§cFailed", 5, 10, 5);
                        return;
                    }

                } else return;
                for (int i = 0; i < 9; i++) player.getInventory().setItem(i, playerManager.getSavedInventory().get(i));
                playerManager.setSelectingAbility(false);
            }
            return;

        }

        if (event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.LAPIS_BLOCK || event.getClickedBlock().getType() == Material.EMERALD_BLOCK)) {

            Material material = event.getClickedBlock().getType();

            Location location = event.getClickedBlock().getLocation();
            location.add(1, 0, 0);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;
            location.add(0, 0, 1);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;
            location.subtract(0, 0, 2);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;
            location.subtract(1, 0, 0);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;
            location.subtract(1, 0, 0);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;
            location.add(0, 0, 1);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;
            location.add(0, 0, 1);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;
            location.add(1, 0, 0);
            if (location.getBlock().getType() != Material.GOLD_BLOCK) return;

            if (material == Material.LAPIS_BLOCK) {

                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (event.getItem() != null) {
                        if (event.getItem().getType() == Material.DIAMOND) {
                            event.setCancelled(true);
                            player.getItemInHand().subtract();
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100, 1f);
                            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, event.getClickedBlock().getLocation(), 100, 0.1, 1, 0.1);
                            playerManager.setExperience(playerManager.getExperience() + 8);
                            player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§7 You just gained §a5 XP§8."));
                            player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§7 You are now §bLevel " + playerManager.getLevel() + "§8."));
                        } else if (event.getItem().getType() == Material.DIAMOND_BLOCK) {
                            event.setCancelled(true);
                            player.getItemInHand().subtract();
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 100, 1f);
                            player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, event.getClickedBlock().getLocation(), 100, 0.1, 1, 0.1);
                            playerManager.setSkill(playerManager.getSkill() + 4);
                            player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§7 You just gained §eⓄ 4 Skill Points§8."));
                            player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§7 You now have a total of §eⓄ " + playerManager.getSkill() + " Skill Points§8."));
                        }
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                        player.openInventory(AbilityTree.buildMainInventory(player, 0));
                    }
                }

            } else if (material == Material.EMERALD_BLOCK) {

                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.setCancelled(true);
                    World world = Bukkit.getWorld("farmworld");
                    if (player.isSneaking()) world = Bukkit.getWorld("farmworld_nether");
                    if (world != null) {
                        player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§7 You have been teleported to the farmworld§8. §7Type §b/leave §7to go back to the overworld§8."));
                        player.teleport(world.getSpawnLocation());
                    } else
                        player.sendMessage(Component.text(Config.MESSAGE_PREFIX + "§c The farmworld is currently not available§8."));

                }

            }

            return;

        }

        if (!player.isSneaking()) return;
        if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) return;
        if (cooldownPlayers.contains(player.getUniqueId())) return;
        cooldownPlayers.add(player.getUniqueId());
        event.setCancelled(true);

        playerManager.setSelectingAbility(true);
        player.sendTitle("", "§6Abilities", 5, 10, 5);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1.5f);

        ArrayList<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 9; i++) items.add(player.getInventory().getItem(i));
        playerManager.setSavedInventory(items);
        for (int i = 0; i < 9; i++) player.getInventory().setItem(i, ItemListUI.FILLER);

        player.getInventory().setItem(0, ItemListUI.CANCEL);

        for (int i = 0; i < playerManager.getActiveAbilities().size(); i++) {

            Ability ability = playerManager.getActiveAbilities().get(i);
            ItemStack item = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            if (playerManager.getMana() < ability.getManaCost()) item.setType(Material.ORANGE_STAINED_GLASS_PANE);
            if (playerManager.getCooldownAbilities().contains(ability)) item.setType(Material.RED_STAINED_GLASS_PANE);
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setDisplayName(ability.getFullName() + " §7(§aLVL " + playerManager.getAbilityLevel(ability) + "§7) §8- §b" + ability.getManaCost() + " Mana");
            if (playerManager.getMana() < ability.getManaCost())
                itemMeta.setDisplayName(ability.getFullName() + " §7(§aLVL " + playerManager.getAbilityLevel(ability) + "§7) §8- §b" + ability.getManaCost() + " Mana");
            if (playerManager.getCooldownAbilities().contains(ability))
                itemMeta.setDisplayName(ability.getFullName() + " §7(§aLVL " + playerManager.getAbilityLevel(ability) + "§7) §8- §6Cooldown");
            itemMeta.setLore(List.of("§7" + ability.getName()));
            item.setItemMeta(itemMeta);
            player.getInventory().setItem(1 + i, item);

        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HyperSpecies.getInstance(), () -> cooldownPlayers.remove(player.getUniqueId()), 5);

    }

}
