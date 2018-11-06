package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Component used to backup config file.
 *
 * @author Darknuju
 * @version 1.0
 * @since 11/5/2018
 *
 */
public class BackupComponent extends ConfigComponent {

    /**
     *
     * Basic constructor.
     *
     * @param componentConfig Config component is bound to.
     *
     */
    public BackupComponent(EasyConfig componentConfig) {
        super(componentConfig);
    }

    /**
     *
     * Backup function. Creates backup file of config file.
     *
     * @param includeDate Determines whether backup file name includes date created. False by default.
     * @return Returns whether backup file was successfully created.
     *
     */
    public boolean backup(boolean includeDate) {
        File mainFile = getComponentConfig().getRawConfigFile();
        String suffix = includeDate ? " - backup " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".yml" : " - backup.yml";
        File backupFile = new File(mainFile.getPath().replace(".yml", suffix));
        try {
            backupFile.delete();
            Files.copy(mainFile.toPath(), backupFile.toPath());
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    /**
     *
     * Backup function. Creates backup file of config file.
     *
     * @return Returns whether backup file was successfully created.
     *
     */
    public boolean backup() {
        return backup(false);
    }
}
