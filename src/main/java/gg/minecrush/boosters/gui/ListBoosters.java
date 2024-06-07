package gg.minecrush.boosters.gui;

import gg.minecrush.boosters.config.Config;
import gg.minecrush.boosters.config.MessagesConfig;
import gg.minecrush.boosters.database.json.playerdata.Json;
import gg.minecrush.boosters.database.json.playerdata.Booster;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static gg.minecrush.boosters.util.TimeFormatter.formatTime;

public class ListBoosters implements Listener {

    private final Json json;
    private final MessagesConfig messagesConfig;
    private final int ITEMS_PER_PAGE = 28; // 9 items per row, 4 rows for boosters
    private final Config config;

    public ListBoosters(Json json, MessagesConfig messagesConfig, Config config) {
        this.json = json;
        this.messagesConfig = messagesConfig;
        this.config = config;
    }

    public void createBoosterGui(Player player, int page) {
        Inventory boosterGui = Bukkit.createInventory(null, 54, "Global Boosters - Page " + (page + 1));

        ItemStack darkGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta darkGlassMeta = darkGlass.getItemMeta();
        if (darkGlassMeta != null) {
            darkGlassMeta.setDisplayName(" ");
            darkGlass.setItemMeta(darkGlassMeta);
        }

        for (int i = 0; i < 9; i++) {
            boosterGui.setItem(i, darkGlass);
        }
        for (int i = 45; i < 54; i++) {
            boosterGui.setItem(i, darkGlass);
        }
        for (int i = 0; i <= 45; i += 9) {
            boosterGui.setItem(i, darkGlass);
        }
        for (int i = 8; i <= 53; i += 9) {
            boosterGui.setItem(i, darkGlass);
        }

        if (!json.hasAnyBoosters(player)) {
            ItemStack noBoosters = new ItemStack(Material.ANVIL);
            ItemStack red = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta noBoostersMeta = noBoosters.getItemMeta();
            if (noBoostersMeta != null) {
                noBoostersMeta.setDisplayName(messagesConfig.getMessages("no-boostName"));
                noBoostersMeta.setLore(messagesConfig.getArrayMessages("no-boostersDescription"));
                noBoosters.setItemMeta(noBoostersMeta);

                ItemStack close = new ItemStack(Material.BARRIER);
                ItemMeta closeMeta = close.getItemMeta();
                if (closeMeta != null) {
                    closeMeta.setDisplayName(messagesConfig.getMessages("closeName"));
                    close.setItemMeta(closeMeta);
                }

                ItemMeta redMeta = red.getItemMeta();
                if (redMeta != null) {
                    redMeta.setDisplayName("§c");
                    red.setItemMeta(redMeta);
                }

                boosterGui.setItem(22, noBoosters);
                boosterGui.setItem(31, red);
                boosterGui.setItem(49, close);
            }
        } else {
            Map<String, Booster> playerBoosters = json.getAllBoosters(player);
            List<Map.Entry<String, Booster>> boosterList = new ArrayList<>(playerBoosters.entrySet());
            int totalItems = boosterList.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

            int start = page * ITEMS_PER_PAGE;
            int end = Math.min(start + ITEMS_PER_PAGE, totalItems);

            int slot = 10;
            for (int i = start; i < end; i++) {
                Map.Entry<String, Booster> entry = boosterList.get(i);
                String boosterName = entry.getKey();
                Booster booster = entry.getValue();
                int amount = booster.getAmount();
                String name = booster.getName();

                String[] parts = boosterName.split("_");
                String id = "0";
                if (parts.length >= 2) {
                    id = parts[1];
                }
                if (amount > 0) {
                    ItemStack boosterItem = new ItemStack(Material.matchMaterial(config.getValue(name + "-item")));
                    ItemMeta boosterMeta = boosterItem.getItemMeta();
                    if (boosterMeta != null) {
                        boosterMeta.setDisplayName("§a" + name.toUpperCase() + " BOOSTER");
                        boosterMeta.setLore(messagesConfig.getReplacedArrayMessage("boostersDescription", id, "" + amount, "" + formatTime(booster.getTime()), "" + booster.getMultiplier()));
                        boosterItem.setItemMeta(boosterMeta);

                        while ((slot % 9 == 0 || slot % 9 == 8) && slot < 45) {
                            slot++;
                        }

                        if (slot >= 45) {
                            break;
                        }

                        boosterGui.setItem(slot, boosterItem);
                        slot++;
                    }
                }
            }

            if (page > 0) {
                ItemStack previousPage = new ItemStack(Material.ARROW);
                ItemMeta previousPageMeta = previousPage.getItemMeta();
                if (previousPageMeta != null) {
                    previousPageMeta.setDisplayName(messagesConfig.getMessages("previousPageName"));
                    previousPage.setItemMeta(previousPageMeta);
                }
                boosterGui.setItem(48, previousPage);
            }

            if (page < totalPages - 1) {
                ItemStack nextPage = new ItemStack(Material.ARROW);
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                if (nextPageMeta != null) {
                    nextPageMeta.setDisplayName(messagesConfig.getMessages("nextPageName"));
                    nextPage.setItemMeta(nextPageMeta);
                }
                boosterGui.setItem(50, nextPage);
            }

            ItemStack close = new ItemStack(Material.BARRIER);
            ItemMeta closeMeta = close.getItemMeta();
            if (closeMeta != null) {
                closeMeta.setDisplayName(messagesConfig.getMessages("closeName"));
                close.setItemMeta(closeMeta);
            }
            boosterGui.setItem(49, close);
        }

        player.openInventory(boosterGui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        if (inventory == null || !event.getView().getTitle().startsWith("Global Boosters")) return;

        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || !currentItem.hasItemMeta()) return;

        ItemMeta itemMeta = currentItem.getItemMeta();
        if (itemMeta == null || !itemMeta.hasDisplayName()) return;

        String displayName = itemMeta.getDisplayName();

        int currentPage = Integer.parseInt(event.getView().getTitle().split(" ")[3]) - 1;
        if (displayName.equals(messagesConfig.getMessages("nextPageName"))) {
            createBoosterGui(player, currentPage);
        } else if (displayName.equals(messagesConfig.getMessages("previousPageName"))) {
            createBoosterGui(player, currentPage - 2);
        }
    }
}