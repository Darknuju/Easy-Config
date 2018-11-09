package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * Component used to create automated backup system.
 *
 * @author Darknuju
 * @version 1.0
 * @since 11/5/2018
 * 
 */
public class BackupRunnableComponent extends ConfigComponent {

    private BackupComponent backup;

    /**
     *
     * Basic constructor.
     *
     * @param componentConfig Config component is bound to.
     * @param backup Backup component used to backup.
     *
     */
    public BackupRunnableComponent(EasyConfig componentConfig, BackupComponent backup) {
        super(componentConfig, "backup_runnable");
        this.backup = backup;
    }

    /**
     *
     * Gets backup runnable.
     *
     * @param func Callback function called every time runnable is run.
     * @param date Boolean to determine whether config backup uses date in file name.
     * @return Runnable that backs up config file when run.
     *
     */
    public BukkitRunnable getRunnable(Runnable func, boolean date) {
        return new BackupRunnable(backup, func, date);
    }

    private class BackupRunnable extends BukkitRunnable {
        BackupComponent backup;
        Runnable callback;
        boolean date;

        BackupRunnable(BackupComponent backup, Runnable callback, boolean date) {
            this.backup = backup;
            this.callback = callback;
            this.date = date;
        }

        @Override
        public void run() {
            backup.backup(date);
            if (callback != null) {
                callback.run();
            }
        }
    }

}
