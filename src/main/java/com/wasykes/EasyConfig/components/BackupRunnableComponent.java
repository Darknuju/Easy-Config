package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
import org.bukkit.scheduler.BukkitRunnable;

public class BackupRunnableComponent extends ConfigComponent {

    private BackupComponent backup;

    public BackupRunnableComponent(EasyConfig componentConfig, BackupComponent backup) {
        super(componentConfig);
        this.backup = backup;
    }

    public BukkitRunnable getRunnable(Runnable func) {
        return new BackupRunnable(backup, func);
    }

    private class BackupRunnable extends BukkitRunnable {
        BackupComponent backup;
        Runnable callback;

        BackupRunnable(BackupComponent backup, Runnable callback) {
            this.backup = backup;
            this.callback = callback;
        }

        @Override
        public void run() {
            backup.backup();
            if (Util.IsNotNull(callback)) {
                callback.run();
            }
        }
    }

}
