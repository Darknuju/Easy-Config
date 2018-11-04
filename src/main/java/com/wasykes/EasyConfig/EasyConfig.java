package com.wasykes.EasyConfig;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * The main class of the Easy Config library.
 *
 * @author Darknuju
 * @version 1.0
 * @since 10/29/2018
 *
 */
public class EasyConfig {

    File rawConfigFile;
    YamlConfiguration yamlConfig;
    Map<String, Object> values;
    ArrayList<ConfigComponent> configComponentList;

    public File getRawConfigFile()  {
        return rawConfigFile;
    }
    public YamlConfiguration getYamlConfig() {
        return yamlConfig;
    }
    public Object getValue(String path) {
        return values.get(path);
    }
    public void setValue(String path, Object value) {
        values.put(path, value);
    }

    /**
     *
     * Get's all paths loaded into memory.
     *
     * @return Set of strings of each path.
     *
     */
    public Set<String> getPaths() {
        return values.keySet();
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created. Reads from file if loadAllValuesToMemory is true.
     *
     * @param rawConfigFile File object for config.
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically.
     * @throws IOException Throws IOException if unable to create or access file.
     *
     */
    public EasyConfig(File rawConfigFile, boolean loadAllValuesToMemory) throws IOException {
        if (!rawConfigFile.getParentFile().exists()) {
            rawConfigFile.getParentFile().mkdir();
        }
        if (!rawConfigFile.exists()) {
            rawConfigFile.createNewFile();
        }

        this.rawConfigFile = rawConfigFile;
        this.yamlConfig = YamlConfiguration.loadConfiguration(this.rawConfigFile);
        values = new HashMap<>();
        configComponentList = new ArrayList<>();

        if (loadAllValuesToMemory) {
            for(String key : yamlConfig.getKeys(true)) {
                Object o = yamlConfig.get(key);
                if (o != null && !(o instanceof MemorySection)){
                    values.put(key, o);
                }
            }
        }
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created.
     *
     * @param rawConfigFile File object for config.
     * @throws IOException Throws IOException if unable to create or access file.
     *
     */
    public EasyConfig(File rawConfigFile) throws IOException {
        this(rawConfigFile, false);
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created.
     *
     * @param path String path for config file.
     * @throws IOException Throws IOException if unable to create or access file.
     *
     */
    public EasyConfig(String path) throws IOException {
        this(new File(path));
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created.
     *
     * @param path String path for config file.
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically.
     * @throws IOException Throws IOException if unable to create or access file.
     *
     */
    public EasyConfig(String path, boolean loadAllValuesToMemory) throws IOException {
        this(new File(path), loadAllValuesToMemory);
    }

    /**
     *
     * Loads value from path in config into memory which can be retrieved or changed using getValue or setValue.
     * Reads from disk.
     *
     * @param path  Configuration path.
     * @return Boolean indicating whether or not value existed at path and was successfully loaded.
     *
     */
    public boolean loadConfigurationIntoMemory(String path) {
        boolean returnVal = false;

        if (!Util.IsNotNull(yamlConfig)) {
            return returnVal;
        }

        if (yamlConfig.contains(path)) {
            values.put(path, yamlConfig.get(path));
            returnVal = true;
        } else {
            returnVal = false;
        }

        update();
        return returnVal;
    }

    /**
     *
     * Unloads from memory and writes updated value to config.
     * Writes to disk.
     *
     * @param path Configuration path.
     * @return Boolean indicating whether or not value existed at path and was successfully unloaded.
     * @throws IOException Throws exception when writing data.
     *
     */
    public boolean unloadConfigurationFromMemory(String path) throws IOException {
        boolean returnVal = false;

        if (!Util.IsNotNull(yamlConfig, rawConfigFile, values)) {
            return returnVal;
        }

        if (values.keySet().contains(path)) {
            yamlConfig.set(path, values.get(path));
            yamlConfig.save(rawConfigFile);
            returnVal = true;
        } else {
            returnVal = false;
        }

        update();
        return returnVal;
    }

    /**
     *
     * Checks if value is loaded to memory.
     *
     * @param path Configuration path.
     * @return Boolean whether value is loaded or not.
     *
     */
    public boolean isLoadedToMemory(String path) {
        return values.keySet().contains(path);
    }

    /**
     *
     * Updates all config components.
     *
     */
    private void update() {
        configComponentList.forEach((component) -> component.setComponentConfig(this));
    }

    /**
     *
     * Unloads all values currently in memory and writes them all to config.
     * Writes to disk.
     * @throws IOException Throws IOException if saving to config fails.
     *
     */
    public void unloadAllValues() throws IOException {
        for(String path : values.keySet()) {
            yamlConfig.set(path, values.get(path));
        }
        yamlConfig.save(rawConfigFile);
    }

    /**
     *
     * Add's components allowing for more functionality tot he config.
     *
     * @param component Component to be added.
     *
     */
    public void addConfigComponent(ConfigComponent component) {
        configComponentList.add(component);
    }

}
