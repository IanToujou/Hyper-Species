package net.toujoustudios.hyperspecies.ui;

import net.toujoustudios.hyperspecies.ability.active.Ability;
import net.toujoustudios.hyperspecies.ability.tree.AbilityTree;
import net.toujoustudios.hyperspecies.config.Config;
import net.toujoustudios.hyperspecies.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class AbilityTreeUI implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().startsWith("Ability Trees")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            if (event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            int page = Integer.parseInt(event.getView().getTitle().split(" ")[3]);

            if (material == Material.NETHER_STAR) {

                String treeName = name.split(" ", 2)[1];
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

                AbilityTree tree = AbilityTree.getTree(treeName);

                if (tree != null) {
                    player.openInventory(tree.buildInventory(player));
                }

            } else if (material == Material.BARRIER) {

                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);

            } else if (material == Material.PLAYER_HEAD) {

                if (name.contains("Back")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                    player.openInventory(AbilityTree.buildMainInventory(player, page - 2));
                } else if (name.contains("Next")) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                    player.openInventory(AbilityTree.buildMainInventory(player, page));
                }

            } else if (material == Material.TNT) {

                player.sendMessage(Config.MESSAGE_PREFIX + "§7 Your loadout has been reset§8.");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 10, 2f);
                PlayerManager playerManager = PlayerManager.get(player);
                playerManager.getActiveAbilities().clear();
                player.closeInventory();

            }

        } else if (event.getView().getTitle().startsWith("Tree: ")) {

            if (event.getCurrentItem() == null) return;
            event.setCancelled(true);

            Material material = event.getCurrentItem().getType();
            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();

            if (itemMeta == null) return;

            if (material == Material.BARRIER) {

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 0.5f);
                player.openInventory(AbilityTree.buildMainInventory(player, 0));

            } else if (material == Material.RED_WOOL) {

                String abilityName = itemMeta.getDisplayName().split(" ", 2)[1];
                String[] abilityNameArray = abilityName.split(" ");
                StringBuilder nameBuilder = new StringBuilder();
                for (String s : abilityNameArray) {
                    if (!s.contains("§")) nameBuilder.append(" ").append(s);
                }
                abilityName = nameBuilder.substring(1);

                if (Ability.getAbility(abilityName) == null) {
                    abilityName = itemMeta.getDisplayName().split(" ", 3)[2];
                    abilityNameArray = abilityName.split(" ");
                    nameBuilder = new StringBuilder();
                    for (String s : abilityNameArray) {
                        if (!s.contains("§")) nameBuilder.append(" ").append(s);
                    }
                    abilityName = nameBuilder.substring(1);
                }

                Ability ability = Ability.getAbility(abilityName);
                if (ability == null) return;
                PlayerManager playerManager = PlayerManager.get(player);

                if (playerManager.getSkill() < ability.getCost()) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 0f);
                    player.sendMessage(Config.MESSAGE_PREFIX + "§c You don't have enough skill points§8.");
                    player.openInventory(AbilityTree.buildMainInventory(player, 0));
                    return;
                }

                playerManager.unlockAbility(ability);
                playerManager.setSkill(playerManager.getSkill() - ability.getCost());
                player.openInventory(AbilityTree.buildMainInventory(player, 0));

            } else if (itemMeta.getDisplayName().startsWith("§") && material != Material.GRAY_WOOL) {

                String abilityName = itemMeta.getDisplayName().split(" ", 2)[1];
                String[] abilityNameArray = abilityName.split(" ");
                StringBuilder nameBuilder = new StringBuilder();
                for (String s : abilityNameArray) {
                    if (!s.contains("§")) nameBuilder.append(" ").append(s);
                }
                abilityName = nameBuilder.substring(1);

                if (Ability.getAbility(abilityName) == null) {
                    abilityName = itemMeta.getDisplayName().split(" ", 3)[2];
                    abilityNameArray = abilityName.split(" ");
                    nameBuilder = new StringBuilder();
                    for (String s : abilityNameArray) {
                        if (!s.contains("§")) nameBuilder.append(" ").append(s);
                    }
                    abilityName = nameBuilder.substring(1);
                }

                Ability ability = Ability.getAbility(abilityName);
                if (ability == null) return;
                PlayerManager playerManager = PlayerManager.get(player);

                if (playerManager.getActiveAbilities().contains(ability)) {
                    playerManager.removeActiveAbility(ability);
                    player.sendMessage(Config.MESSAGE_PREFIX + "§7 You removed §b" + ability.getName() + "§7 from your loadout§8.");
                    player.sendMessage(Config.MESSAGE_PREFIX + "§7 New weight§8: §5" + playerManager.getAbilityWeight() + " §8/§5 " + playerManager.getMaxAbilityWeight());
                } else {
                    if (playerManager.getAbilityWeight() + ability.getWeight() > playerManager.getMaxAbilityWeight()) {
                        player.openInventory(AbilityTree.buildMainInventory(player, 0));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 0f);
                        player.sendMessage(Config.MESSAGE_PREFIX + "§c You cannot carry a loadout this powerful§8.");
                        player.sendMessage(Config.MESSAGE_PREFIX + "§7 Current weight§8: §5" + playerManager.getAbilityWeight() + " §8/§5 " + playerManager.getMaxAbilityWeight());
                        return;
                    }
                    if (playerManager.getActiveAbilities().size() >= 8) {
                        player.openInventory(AbilityTree.buildMainInventory(player, 0));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 100, 0f);
                        player.sendMessage(Config.MESSAGE_PREFIX + "§c You cannot carry more than §b8§c abilities§8.");
                        return;
                    }
                    playerManager.addActiveAbility(ability);
                    player.sendMessage(Config.MESSAGE_PREFIX + "§7 You added §b" + ability.getName() + "§7 to your loadout§8.");
                    player.sendMessage(Config.MESSAGE_PREFIX + "§7 New weight§8: §5" + playerManager.getAbilityWeight() + " §8/§5 " + playerManager.getMaxAbilityWeight());
                }

                player.openInventory(AbilityTree.buildMainInventory(player, 0));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.MASTER, 100, 2f);

            }

        }

    }

}
