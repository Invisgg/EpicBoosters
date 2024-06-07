package gg.minecrush.boosters.database.yml;

import java.io.File;

import gg.minecrush.boosters.Boosters;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class ConfigFile {
    private final String path;
    private final File file;
    private FileConfiguration data;

    protected ConfigFile(String subfolder, String filename) {
        this.path = "plugins/EpicBoosters/" + subfolder + "/" + filename + ".yml";
        this.file = new File(this.path);
        this.data = YamlConfiguration.loadConfiguration(this.file);
        this.init();
    }


    public abstract void onFirstLoad();

    public void init() {
        try {
            if (!this.file.getParentFile().exists()) {
                this.file.getParentFile().mkdirs();
            }

            if (!this.file.exists()) {
                this.file.createNewFile();
                this.onFirstLoad();
            }
        } catch (Exception var2) {
            Boosters.log.warning("cannot create config file for '" + this.file.getPath() + "'");
        }

    }

    public void reload() {
        this.data = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.data.save(this.file);
        } catch (Exception var2) {
            Boosters.log.warning("cannot save config file for '" + this.file.getPath() + "'");
        }

    }

    public String path() {
        return this.path;
    }

    public File file() {
        return this.file;
    }

    public FileConfiguration get() {
        return this.data;
    }
}
