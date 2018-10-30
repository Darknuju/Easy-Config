package com.wasykes.EasyConfig;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *  The main class of the Easy Config library
 *
 *  @author Darknuju
 *  @version 1.0
 *  @since 10/29/2018
 *
 */
public class EasyConfig {

    File rawConfigFile;
    YamlConfiguration yamlConfig;
    public Map<String, Object> values;

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
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist
     * Writes to disk if file not created. Reads from file if loadAllValuesToMemory is true
     *
     * @param rawConfigFile File object for config
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically
     * @throws IOException Throws IOException if unable to create or access file
     *
     */
    public EasyConfig(File rawConfigFile, boolean loadAllValuesToMemory) throws IOException {
        if (!rawConfigFile.exists()) {
            rawConfigFile.createNewFile();
        }

        this.rawConfigFile = rawConfigFile;
        this.yamlConfig = YamlConfiguration.loadConfiguration(this.rawConfigFile);
        values = new HashMap<>();

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
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist
     * Writes to disk if file not created
     *
     * @param rawConfigFile File object for config
     * @throws IOException Throws IOException if unable to create or access file
     *
     */
    public EasyConfig(File rawConfigFile) throws IOException {
        this(rawConfigFile, false);
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist
     * Writes to disk if file not created
     *
     * @param path String path for config file
     * @throws IOException Throws IOException if unable to create or access file
     *
     */
    public EasyConfig(String path) throws IOException {
        this(new File(path));
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist
     * Writes to disk if file not created
     *
     * @param path String path for config file
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically
     * @throws IOException Throws IOException if unable to create or access file
     *
     */
    public EasyConfig(String path, boolean loadAllValuesToMemory) throws IOException {
        this(new File(path), loadAllValuesToMemory);
    }

    /**
     *
     * Loads value from path in config into memory which can be retrieved or changed using getValue or setValue
     * Reads from disk
     *
     * @param path  Configuration path
     * @return Boolean indicating whether or not value existed at path and was successfully loaded
     *
     */
    public boolean loadConfigurationIntoMemory(String path) {
        if (!Util.IsNotNull(yamlConfig)) {
            return false;
        }

        if (yamlConfig.contains(path)) {
            setValue(path, yamlConfig.get(path));
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Unloads from memory and writes updated value to config
     * Writes to disk
     *
     * @param path Configuration path
     * @return Boolean indicating whether or not value existed at path and was successfully unloaded
     * @throws IOException Throws exception when writing data
     *
     */
    public boolean unloadConfigurationFromMemory(String path) throws IOException {
        if (!Util.IsNotNull(yamlConfig, rawConfigFile, values)) {
            return false;
        }

        if (values.keySet().contains(path)) {
            yamlConfig.set(path, values.get(path));
            yamlConfig.save(rawConfigFile);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Checks if value is loaded to memory
     *
     * @param path Configuration path
     * @return Boolean whether value is loaded or not
     *
     */
    public boolean isLoadedToMemory(String path) {
        return values.keySet().contains(path);
    }

}
