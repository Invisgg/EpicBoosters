package gg.minecrush.boosters.database.json.values;

import gg.minecrush.boosters.Bossbar.BossBarManager;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ValuesClass {

    private int multiplier = 0;
    private String host = "";
    private boolean globalActive = false;
    private String activeBooster = "";
    private BossBar bossBar;
    private final Logger logger = Logger.getLogger("ValuesClass");
    private final Plugin plugin;
    private final File configFile;
    private FileConfiguration config;

    public ValuesClass(Plugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "values.yml");
        if (!configFile.exists()) {
            plugin.saveResource("values.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
        loadValues();
    }

    public int getMultiplier() {
        return multiplier;
    }

    public String getHost() {
        return host;
    }

    public boolean isGlobalActive() {
        return globalActive;
    }

    public String getActiveBooster() {
        return activeBooster;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
        saveValues();
    }

    public void setHost(String host) {
        this.host = host;
        saveValues();
    }

    public void setGlobalActive(boolean globalActive) {
        this.globalActive = globalActive;
        saveValues();
    }

    public void setActiveBooster(String activeBooster) {
        this.activeBooster = activeBooster;
        saveValues();
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
        saveValues();
    }

    public void saveValues() {
        config.set("multiplier", multiplier);
        config.set("host", host);
        config.set("globalActive", globalActive);
        config.set("activeBooster", activeBooster);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValueString(String value){
        String data = config.getString(value);
        if (data != null) {
            return data;
        }
        return null;
    }

    public boolean getValueBoolean(String value){
        String data = config.getString(value);
        if (data != null) {
            return Boolean.parseBoolean(data);
        }
        return false;
    }

    public int getValueInt(String value){
        String data = config.getString(value);
        if (data != null) {
            return Integer.parseInt(data);
        }
        return 0;
    }

    private void loadValues() {
        multiplier = config.getInt("multiplier");
        host = config.getString("host");
        globalActive = config.getBoolean("globalActive");
        activeBooster = config.getString("activeBooster");
    }
}
