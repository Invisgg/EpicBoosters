package gg.minecrush.boosters.Bossbar;

import gg.minecrush.boosters.database.json.values.ValuesClass;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class JoinEvents implements Listener {

    private final ValuesClass values;
    Plugin plugin = this.plugin;
    private BossBar bossBar;

    public JoinEvents(ValuesClass values, Plugin plugin) {
        this.values = values;
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (values.isGlobalActive() == true){
            bossBar = values.getBossBar();
            bossBar.addPlayer(player);
        }
    }
}
