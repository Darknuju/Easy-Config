import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.components.BackupComponent;
import com.wasykes.EasyConfig.components.BackupRunnableComponent;
import org.bukkit.scheduler.BukkitRunnable;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduledBackupTest {

    @Test
    public void testScheduledBackupCreatesFile() throws IOException {
        File rawConfigFile = new File("./testConfigs/sheduledConfig.yml");
        File rawConfigFileBackup = new File("./testConfigs/sheduledConfig-backup.yml");
        EasyConfig config = new EasyConfig(rawConfigFile);
        BackupComponent backup = new BackupComponent(config);
        BackupRunnableComponent backupRunnable = new BackupRunnableComponent(config, backup);
        BukkitRunnable runnable = backupRunnable.getRunnable(() -> {
            Assert.assertTrue("File should exist!", rawConfigFileBackup.exists());
            rawConfigFileBackup.delete();
            rawConfigFile.delete();
            rawConfigFile.getParentFile().delete();
        }, false);
        runnable.run();
    }

    @Test
    public void testScheduledBackupCreatesFileWithDate() throws IOException {
        File rawConfigFile = new File("./testConfigs/sheduledConfig.yml");
        File rawConfigFileBackup = new File("./testConfigs/sheduledConfig-backup-" + new SimpleDateFormat("MM-dd-yyyy").format(new Date()) + ".yml");
        EasyConfig config = new EasyConfig(rawConfigFile);
        BackupComponent backup = new BackupComponent(config);
        BackupRunnableComponent backupRunnable = new BackupRunnableComponent(config, backup);
        BukkitRunnable runnable = backupRunnable.getRunnable(() -> {
            Assert.assertTrue("File should contain date!", rawConfigFileBackup.getPath().contains(new SimpleDateFormat("MM-dd-yyyy").format(new Date())));
            rawConfigFileBackup.delete();
            rawConfigFile.delete();
            rawConfigFile.getParentFile().delete();
        }, true);
        runnable.run();
    }

}
