package gg.minecrush.boosters.Bossbar;

import gg.minecrush.boosters.config.MessagesConfig;
import gg.minecrush.boosters.database.json.values.ValuesClass;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static gg.minecrush.boosters.util.TimeFormatter.formatTime;

public class BossBarManager {
    private BossBar bossBar;
    private Plugin plugin;

    private ValuesClass value;
    private MessagesConfig messagesConfig;

    public BossBarManager(Plugin plugin, ValuesClass value, MessagesConfig messagesConfig) {
        this.plugin = plugin;
        this.value = value;
        this.messagesConfig = messagesConfig;
    }

    public void createBossBar(String message, BarColor color, BarStyle style, double progress) {
        if (bossBar != null) {
            bossBar.removeAll();
        }

        bossBar = Bukkit.createBossBar(message, color, style);
        bossBar.setProgress(progress);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        bossBar.setVisible(true);
        value.setBossBar(bossBar);
    }

    public void updateProgress(int duration) {
        new BukkitRunnable() {
            int remainingTime = duration;

            @Override
            public void run() {
                if (remainingTime <= 0) {
                    bossBar.removeAll();
                    bossBar.setVisible(false);
                    value.setGlobalActive(false);
                    value.setActiveBooster("");
                    value.setHost("");
                    value.setMultiplier(1);
                    cancel();
                } else {
                    boolean val = value.getValueBoolean("globalActive");
                    if (val == true){
                        double progress = (double) remainingTime / duration;
                        bossBar.setProgress(progress);
                        String message = messagesConfig.getMessages("bossbarMessage")
                                .replace("%p%", messagesConfig.getMessages("prefix"))
                                            .replace("%player%",value.getHost())
                                        .                                   replace("%type%", capitalizeFirstLetter(value.getActiveBooster()))
                                .replace("%multi%", "" + value.getMultiplier())
                                                                    .replace("%time%", formatTime(remainingTime));
                        bossBar.setTitle(message);
                        remainingTime--;

                    } else {
                        bossBar.removeAll();
                        bossBar.setVisible(false);
                        value.setGlobalActive(false);
                        value.setActiveBooster("");
                        value.setHost("");
                        value.setMultiplier(1);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void removeBossBar() {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar.setVisible(false);
            bossBar = null;
        }
    }
}
