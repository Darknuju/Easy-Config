package com.wasykes.EasyConfig.components;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.Util;
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
     * @return Runnable that backs up config file when run.
     *
     */
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
