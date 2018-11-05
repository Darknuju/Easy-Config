import com.wasykes.EasyConfig.EasyConfig;
import org.bukkit.Material;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TypeStoreTest {

    @Test
    public void testStoresString() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        config.setValue("String.store", "This is a test string");
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("String.store");
        Assert.assertEquals("Should equal This is a test string", "This is a test string", config.getValue("String.store"));
    }

    @Test
    public void testStoresInt() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        config.setValue("Int.store", 50);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("Int.store");
        Assert.assertEquals("Should equal 50", 50, config.getValue("Int.store"));
    }

    @Test
    public void testStoresEnum() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        config.setValue("Enum.store", Material.DIRT);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("Enum.store");
        Assert.assertEquals("Should equal Material.DIRT", Material.DIRT, config.getValue("Enum.store"));
    }

    @Test
    public void testStoreDouble() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        config.setValue("Double.store", 1.5);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("Double.store");
        Assert.assertEquals("Should equal 1.5", 1.5, config.getValue("Double.store"));
    }

    @Test
    public void testStoreListString() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        ArrayList<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        config.setValue("List.store", list);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("List.store");
        Assert.assertEquals("Should equal test1", "test1", ((ArrayList<String>)config.getValue("List.store")).get(0));
    }

    @Test
    public void testStoreListInt() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(100);
        config.setValue("List.store", list);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("List.store");
        Assert.assertEquals("Should equal 1", 1, (int)((ArrayList<Integer>)config.getValue("List.store")).get(0));
    }

    @Test
    public void testStoreListEnum() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        ArrayList<Material> list = new ArrayList<>();
        list.add(Material.APPLE);
        list.add(Material.BONE);
        config.setValue("List.store", list);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("List.store");
        Assert.assertEquals("Should equal Material.APPLE", Material.APPLE, ((ArrayList<Material>)config.getValue("List.store")).get(0));
    }

    @Test
    public void testStoreListDouble() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testTypes.yml");
        ArrayList<Double> list = new ArrayList<>();
        list.add(1.5);
        list.add(2.9);
        config.setValue("List.store", list);
        config.unloadAllValues();
        config.loadConfigurationIntoMemory("List.store");
        Assert.assertEquals("Should equal 1.5", (Object) 1.5, (Object)((ArrayList<Double>)config.getValue("List.store")).get(0));
    }

    @After
    public void deleteConfig() {
        File fileToDelete = new File("./testConfigs/testTypes.yml");
        fileToDelete.delete();
        File folderToDelete = new File("./testConfigs");
        folderToDelete.delete();
    }
}
