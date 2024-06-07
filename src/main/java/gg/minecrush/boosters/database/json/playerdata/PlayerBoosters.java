package gg.minecrush.boosters.database.json.playerdata;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerBoosters {
    private UUID playerUUID;
    private Map<String, Booster> boosters = new HashMap<>();

    public PlayerBoosters(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public File getFile() {
        return new File("plugins/EpicBoosters/database/" + playerUUID.toString() + ".json");
    }

    public Map<String, Booster> getBoosters() {
        return boosters;
    }

    public void addBooster(String name, int amount, int time, int multiplier) {
        String boosterName = name;
        int index = 1;
        while (boosters.containsKey(boosterName)) {
            boosterName = name + "_" + index;
            index++;
        }

        Booster booster = boosters.getOrDefault(boosterName, new Booster(0, time, multiplier, name));

        booster.setAmount(booster.getAmount() + amount);
        booster.setTime(time);
        booster.setMultiplier(multiplier);
        booster.setName(name);
        boosters.put(boosterName, booster);
    }

    public void removeBooster(String key) {
        boosters.remove(key);
    }

    public Booster getBooster(String key) {
        return boosters.get(key);
    }

    public void updateBooster(String key, Booster updatedBooster) {
        boosters.put(key, updatedBooster);
    }

    public int getBoosterAmount(String name) {
        return boosters.getOrDefault(name, new Booster(0, 0, 0, name)).getAmount();
    }

    public boolean hasAnyBoosters() {
        return !boosters.isEmpty();
    }


}
