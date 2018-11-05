import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.components.BackupComponent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupComponentTest {

    @Test
    public void testBackupFileExists() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/backupConfig.yml");
        BackupComponent backup = new BackupComponent(config);
        Assert.assertTrue("Should return true!", backup.backup());
    }

    @Test
    public void testBacksUpWithDate() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/backupConfig.yml");
        BackupComponent backup = new BackupComponent(config);
        backup.backup(true);
        File f = new File("./testConfigs/backupConfig - backup " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".yml");
        Assert.assertTrue("Should contain date (exist)!", f.exists());
    }

    @Test
    public void testNewInformationBackedUp() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/backupConfig.yml");
        BackupComponent backup = new BackupComponent(config);
        backup.backup();
        config.setValue("Test", 99);
        config.unloadConfigurationFromMemory("Test");
        backup.backup();
        Assert.assertEquals("Should equal 99!", 99, YamlConfiguration.loadConfiguration(new File("./testConfigs/backupConfig - backup.yml")).getInt("Test"));
    }

    @After
    public void deleteConfig() {
        File fileToDelete = new File("./testConfigs/backupConfig.yml");
        fileToDelete.delete();
        File fileToDelete2 = new File("./testConfigs/backupConfig - backup.yml");
        fileToDelete2.delete();
        File fileToDelete3 = new File("./testConfigs/backupConfig - backup " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".yml");
        fileToDelete3.delete();
        File folderToDelete = new File("./testConfigs");
        folderToDelete.delete();
    }
}
