import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.EasyConfigPlugin;
import com.wasykes.EasyConfig.components.BackupComponent;
import com.wasykes.EasyConfig.components.BackupRunnableComponent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ScheduledBackupTest {

    @Test
    public void testScheduledBackupCreatesFile() throws IOException {
        File rawConfigFile = new File("./testConfigs/sheduledConfig.yml");
        File rawConfigFileBackup = new File("./testConfigs/sheduledConfig - backup.yml");
        EasyConfig config = new EasyConfig(rawConfigFile);
        BackupComponent backup = new BackupComponent(config);
        BackupRunnableComponent backupRunnable = new BackupRunnableComponent(config, backup);
        BukkitRunnable runnable = backupRunnable.getRunnable(() -> {
            Assert.assertTrue("File should exist!", rawConfigFileBackup.exists());
            rawConfigFileBackup.delete();
            rawConfigFile.delete();
            rawConfigFile.getParentFile().delete();
        });
        runnable.run();
    }

}
