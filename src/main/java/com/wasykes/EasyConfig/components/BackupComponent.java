package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
        super(componentConfig, "backup");
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

    /**
     *
     * Used to clean out backup config files before certain date.
     * @param date Delete all files with date until this date.
     * @return Whether files deleted.
     */
    public boolean cleanUpBeforeDate(LocalDate date) {
        File configDir = componentConfig.getRawConfigFile().getParentFile();
        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !(name.equalsIgnoreCase(getComponentConfig().getRawConfigFile().getName()) || name.equalsIgnoreCase(getComponentConfig().getRawConfigFile().getName().replace(".yml", " - backup.yml")));
            }
        };

        String[] files = configDir.list(textFilter);

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                LocalDate fileDate = LocalDate.parse(files[i].substring(files[i].indexOf("p ") + 2, files[i].indexOf(".yml")), formatter);
                if (date.isAfter(fileDate)) {
                    new File(configDir.getPath() + "/" + files[i]).delete();
                    return true;
                }
            }
        }
        return false;
    }
}
