package gg.minecrush.boosters.config;

import gg.minecrush.boosters.util.color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessagesConfig {
    private final Plugin plugin;
    private File configFile;
    private FileConfiguration config;

    public MessagesConfig(Plugin plugin) {
        this.plugin = plugin;
        createConfig();
    }

    private void createConfig() {
        configFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public String getMessages(String key) {
        String message = config.getString(key);
        if (message == null) {
            return "";
        }
        return color.c(message);
    }

    public List<String> getArrayMessages(String key) {
        List<String> messages = config.getStringList(key);
        if (messages == null || messages.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> coloredMessages = new ArrayList<>();
        for (String message : messages) {
            coloredMessages.add(color.c(message));
        }
        return coloredMessages;
    }

    public List<String> getReplacedArrayMessage(String key, String id, String amount, String time, String multiplier) {
        List<String> messages = getArrayMessages(key);
        List<String> replacedMessages = new ArrayList<>();

        for (String message : messages) {
            message = message.replace("%amount%", amount);
            message = message.replace("%time%", time);
            message = message.replace("%multiplier%", multiplier);
            replacedMessages.add(message);
        }
        List<String> finalLoreMessages = new ArrayList<>();
        finalLoreMessages.add("§7§oId: " + id);
        finalLoreMessages.addAll(replacedMessages);
        return finalLoreMessages;
    }

    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
