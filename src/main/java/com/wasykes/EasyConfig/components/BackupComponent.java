package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupComponent extends ConfigComponent {

    public BackupComponent(EasyConfig config) {
        super(config);
    }

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

    public boolean backup() {
        return backup(false);
    }
}
