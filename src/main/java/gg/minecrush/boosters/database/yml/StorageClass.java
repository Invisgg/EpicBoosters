package gg.minecrush.boosters.database.yml;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StorageClass extends ConfigFile {

    private final String subfolder;

    public StorageClass(String subfolder, String uuid) {
        super(subfolder, uuid);
        this.subfolder = subfolder;
    }

    public void setStorage(String key, String value) {
        get().set(key, value);
        save();
    }

    public String getStorage(String key) {
        return get().getString(key);
    }

    public void setList(String key, List<String> values) {
        get().set(key, values);
        save();
    }

    public void setListFromCommaSeparated(String key, String commaSeparatedValues) {
        List<String> values = Arrays.stream(commaSeparatedValues.split(", "))
                .map(String::trim)
                .collect(Collectors.toList());
        setList(key, values);
    }

    public void addValueToList(String key, String value) {
        List<String> currentList = getList(key);
        currentList.add(value);
        setList(key, currentList);
    }

    public void editValueInList(String key, String oldValue, String newValue) {
        List<String> currentList = getList(key);

        int index = currentList.indexOf(oldValue);

        if (index != -1) {
            currentList.set(index, newValue);
            setList(key, currentList);
        } else {
            System.out.println("Value not found in the list for editing.");
        }
    }

    public List<String> getList(String key) {
        return get().getStringList(key);
    }

    @Override
    public void onFirstLoad() {
        save();
    }
}