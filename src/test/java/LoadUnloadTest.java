import com.wasykes.EasyConfig.EasyConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class LoadUnloadTest {

    @Test
    public void testIfValueNotInMemoryWhenUnloaded() throws IOException {
        EasyConfig config = new EasyConfig("testConfigs/loadTest.yml");
        config.setValue("Test", 6);
        config.unloadConfigurationFromMemory("Test");
        Assert.assertFalse("Should be false!", config.isLoadedToMemory("Test"));
    }

    @Test
    public void testIfValueInFileWhenUnloaded() throws IOException {
        EasyConfig config = new EasyConfig("testConfigs/loadTest.yml");
        config.setValue("Test", 6);
        config.unloadConfigurationFromMemory("Test");
        YamlConfiguration yamlCfg = YamlConfiguration.loadConfiguration(new File("testConfigs/loadTest.yml"));
        Assert.assertEquals("Should be true", 6, yamlCfg.get("Test"));
    }

    @Test
    public void testIfValueInMemoryWhenLoaded() throws IOException {
        EasyConfig config = new EasyConfig("testConfigs/loadTest.yml");
        config.setValue("Test", 6);
        config.unloadConfigurationFromMemory("Test");
        config.loadConfigurationIntoMemory("Test");
        Assert.assertTrue("Should be false!", config.isLoadedToMemory("Test"));
    }

    @After
    public void deleteConfig() {
        File fileToDelete = new File("./testConfigs/loadTest.yml");
        fileToDelete.delete();
        File folderToDelete = new File("./testConfigs");
        folderToDelete.delete();
    }

}
