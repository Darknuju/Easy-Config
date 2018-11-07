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

    private File rawConfigFile;
    private YamlConfiguration yamlConfig;
    private Map<String, Object> values;
    private ArrayList<ConfigComponent> configComponentList;

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
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically. False by default.
     * @param generateFile Determines whether a file is generated automatically or not. True by default.
     *
     */
    public EasyConfig(File rawConfigFile, boolean loadAllValuesToMemory, boolean generateFile) {

        if (generateFile) {
            if (!rawConfigFile.getParentFile().exists()) {
                rawConfigFile.getParentFile().mkdir();
            }
            if (!rawConfigFile.exists()) {
                try {
                    rawConfigFile.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
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
     *
     */
    public EasyConfig(File rawConfigFile) {
        this(rawConfigFile, false, true);
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created.
     *
     * @param path String path for config file.
     *
     */
    public EasyConfig(String path) {
        this(new File(path));
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created.
     *
     * @param rawConfigFile File object for config.
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically. False by default.
     *
     */
    public EasyConfig(File rawConfigFile, boolean loadAllValuesToMemory) {
        this(rawConfigFile, loadAllValuesToMemory, true);
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created.
     *
     * @param path String path for config file.
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically.
     *
     */
    public EasyConfig(String path, boolean loadAllValuesToMemory) {
        this(new File(path), loadAllValuesToMemory, true);
    }

    /**
     *
     * Constructs EasyConfig: This includes generating the file specified if it doesn't exist.
     * Writes to disk if file not created.
     *
     * @param path File object for config.
     * @param loadAllValuesToMemory If true all values in the configuration will be loaded into memory automatically. False by default.
     * @param generateFile Determines whether a file is generated automatically or not. True by default.
     *
     */
    public EasyConfig(String path, boolean loadAllValuesToMemory, boolean generateFile) {
        this(new File(path), loadAllValuesToMemory, generateFile);
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
        if (!Util.IsNotNull(yamlConfig)) {
            return false;
        }

        if (yamlConfig.contains(path)) {
            values.put(path, yamlConfig.get(path));
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * Unloads from memory and writes updated value to config.
     * Writes to disk.
     *
     * @param path Configuration path.
     * @return Boolean indicating whether or not value existed at path and was successfully unloaded.
     *
     */
    public boolean unloadConfigurationFromMemory(String path) {
        if (!Util.IsNotNull(yamlConfig, rawConfigFile, values)) {
            return false;
        }

        if (values.keySet().contains(path)) {
            yamlConfig.set(path, values.get(path));
            values.remove(path);
            saveConfigFile();
            return true;
        } else {
            return false;
        }
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
     * Unloads all values currently in memory and writes them all to config.
     * Writes to disk.
     * @throws IOException Throws IOException if saving to config fails.
     *
     */
    public void unloadAllValues() throws IOException {
        for(String path : values.keySet()) {
            yamlConfig.set(path, values.get(path));
        }
        saveConfigFile();
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


    private void saveConfigFile() {
        try {
            yamlConfig.save(rawConfigFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
