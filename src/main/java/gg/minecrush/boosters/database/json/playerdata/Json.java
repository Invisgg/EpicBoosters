package gg.minecrush.boosters.database.json.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class Json {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void addBooster(Player player, String booster, int amount, int time, int multiplier) {
        UUID playerUUID = player.getUniqueId();
        PlayerBoosters playerBoosters = loadPlayerBoosters(playerUUID);
        playerBoosters.addBooster(booster, amount, time, multiplier);
        savePlayerBoosters(playerBoosters);
    }

    public int getBoosterAmount(Player player, String booster) {
        UUID playerUUID = player.getUniqueId();
        PlayerBoosters playerBoosters = loadPlayerBoosters(playerUUID);
        return playerBoosters.getBoosterAmount(booster);
    }


    public Map<String, Booster> getAllBoosters(Player player) {
        UUID playerUUID = player.getUniqueId();
        PlayerBoosters playerBoosters = loadPlayerBoosters(playerUUID);
        return playerBoosters.getBoosters();
    }

    public boolean hasAnyBoosters(Player player) {
        UUID playerUUID = player.getUniqueId();
        PlayerBoosters playerBoosters = loadPlayerBoosters(playerUUID);
        return playerBoosters.hasAnyBoosters();
    }

    public Booster getBooster(Player player, String key) {
        UUID playerUUID = player.getUniqueId();
        PlayerBoosters playerBoosters = loadPlayerBoosters(playerUUID);
        Booster booster = playerBoosters.getBooster(key);
        return booster;
    }

    public void updateBooster(Player player, String key, Booster updatedBooster) {
        UUID playerUUID = player.getUniqueId();
        PlayerBoosters playerBoosters = loadPlayerBoosters(playerUUID);
        playerBoosters.updateBooster(key, updatedBooster);
        savePlayerBoosters(playerBoosters);
    }

    public void removeBooster(Player player, String key) {
        UUID playerUUID = player.getUniqueId();
        PlayerBoosters playerBoosters = loadPlayerBoosters(playerUUID);
        playerBoosters.removeBooster(key);
        savePlayerBoosters(playerBoosters);
    }

    private PlayerBoosters loadPlayerBoosters(UUID playerUUID) {
        File file = new File("plugins/EpicBoosters/database/" + playerUUID.toString() + ".json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                return gson.fromJson(reader, PlayerBoosters.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new PlayerBoosters(playerUUID);
    }

    private void savePlayerBoosters(PlayerBoosters playerBoosters) {
        File file = playerBoosters.getFile();
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(playerBoosters, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
