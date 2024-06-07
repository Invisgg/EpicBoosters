package gg.minecrush.boosters.gui;



import gg.minecrush.boosters.config.MessagesConfig;
import gg.minecrush.boosters.database.json.playerdata.Booster;
import gg.minecrush.boosters.database.json.playerdata.Json;
import gg.minecrush.boosters.booster.BoosterStartEvent;
import gg.minecrush.boosters.database.json.values.ValuesClass;
import gg.minecrush.boosters.gui.ListBoosters;
import gg.minecrush.boosters.util.color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Locale;

import static gg.minecrush.boosters.util.TimeFormatter.formatTime;

public class ClickEvents implements Listener {

    private final Json json;

    private final ValuesClass value;

    private final Plugin plugin;

    private final MessagesConfig messagesConfig;

    private final ListBoosters listBoosters;

    public ClickEvents(Json json, ValuesClass value, Plugin plugin, MessagesConfig messagesConfig, ListBoosters listBoosters) {
        this.json = json;
        this.value = value;
        this.plugin = plugin;
        this.messagesConfig = messagesConfig;
        this.listBoosters = listBoosters;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith("Global Boosters")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null) {
                if (clickedItem.getType() != Material.BLACK_STAINED_GLASS_PANE && !clickedItem.getItemMeta().getDisplayName().equals(" ")) {
                    if (clickedItem.getItemMeta().getDisplayName().endsWith("BOOSTER") && !clickedItem.getItemMeta().getDisplayName().equals(messagesConfig.getMessages("closeName"))) {
                        boolean val = value.getValueBoolean("globalActive");
                        if (!val) {
                            ItemMeta meta = clickedItem.getItemMeta();
                            if (meta != null && meta.hasLore()) {
                                String firstline = meta.getLore().get(0);
                                String[] parts = firstline.split(" ");
                                String id = "";
                                if (parts.length > 1) {
                                    id = parts[1];
                                }

                                String name = meta.getDisplayName();
                                String cleanName = name.replaceAll("§.", "");
                                String[] split = cleanName.split(" BOOSTER");
                                String names = "";
                                if (split.length > 0) {
                                    names = split[0];
                                }

                                names = names.toLowerCase(Locale.ROOT);
                                String key = names + "_" + id;

                                key = key.replaceAll("_0", "");
                                Booster booster = json.getBooster(player, key);
                                if (booster != null) {
                                    int num = booster.getAmount();
                                    int multiplier = booster.getMultiplier();
                                    int time = booster.getTime();

                                    if (num > 1) {
                                        booster.setAmount(num - 1);
                                        json.updateBooster(player, key, booster);
                                    } else {
                                        json.removeBooster(player, key);
                                    }
                                    BoosterStartEvent boosterStartEvent = new BoosterStartEvent(player
                                            , names
                                            , multiplier
                                            , time);
                                    Bukkit
                                            .getPluginManager()
                                            .callEvent(boosterStartEvent);


                                } else {
                                    player.sendMessage(messagesConfig
                                            .getMessages("jsonError")
                                            .replace("%p%", messagesConfig
                                                    .getMessages("prefix")));
                                }
                            }
                        } else {
                            player.sendMessage(messagesConfig
                                    .getMessages("boosterAlreadyActive")
                                    .replace("%p%", messagesConfig
                                            .getMessages("prefix")));
                        }
                        player.closeInventory();
                    } else if (clickedItem.getType() == Material.BARRIER && clickedItem.getItemMeta().getDisplayName().equals(messagesConfig.getMessages("closeName"))) {
                        player.closeInventory();
                    } else if (clickedItem.getType() == Material.ARROW) {
                        int currentPage = Integer.parseInt(event.getView().getTitle().split("Page ")[1]) - 1;
                        String displayName = clickedItem.getItemMeta().getDisplayName();
                        if (displayName.equals(messagesConfig.getMessages("nextPageName"))) {
                            listBoosters.createBoosterGui(player, currentPage + 1);
                        } else if (displayName.equals(messagesConfig.getMessages("previousPageName"))) {
                            listBoosters.createBoosterGui(player, currentPage - 1);
                        }
                    }
                    
                }
            }
        }
    }
}
