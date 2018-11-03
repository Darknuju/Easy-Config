import com.wasykes.EasyConfig.EasyConfig;
import org.bukkit.Material;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class TypeStoreTest {

    @Test
    public void testAbilityToStoreString() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        config.setValue("String.store", "This is a test string");
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("String.store");
        Assert.assertEquals("Should equal This is a test string", "This is a test string", config.getValue("String.store"));
    }

    @Test
    public void testAbilityToStoreInt() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        config.setValue("Int.store", 50);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("Int.store");
        Assert.assertEquals("Should equal 50", 50, config.getValue("Int.store"));
    }

    @Test
    public void testAbilityToStoreEnum() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        config.setValue("Enum.store", Material.DIRT);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("Enum.store");
        Assert.assertEquals("Should equal Material.DIRT", Material.DIRT, config.getValue("Enum.store"));
    }

    @After
    public void deleteConfig() {
        File fileToDelete = new File("./testConfigs/testTypes.yml");
        Assert.assertTrue("Should delete config file!", fileToDelete.delete());
        File folderToDelete = new File("./testConfigs");
        Assert.assertTrue("Should delete config folder!", folderToDelete.delete());
    }
}
