import com.wasykes.EasyConfig.EasyConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ConstructorTest {

    @Test
    public void testConstructingWithFileObjectAndLoadAllValuesSetToTrueNotNull() throws IOException {
        EasyConfig config = new EasyConfig(new File("./testConfigs/config.yml"), true);
        Assert.assertNotNull("Should not be null", config);
    }

    @Test
    public void testConstructingWithStringAndLoadAllValuesSetToTrueNotNull() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/config.yml", true);
        Assert.assertNotNull("Should not be null", config);
    }

    @Test
    public void testConstructingWithFileObjectNotNull() throws IOException {
        EasyConfig config = new EasyConfig(new File("./testConfigs/config.yml"));
        Assert.assertNotNull("Should not be null", config);
    }

    @Test
    public void testConstructingWithStringNotNull() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/config.yml");
        Assert.assertNotNull("Should not be null", config);
    }

    @Test
    public void testConstructingWithFileObjectAndLoadAllValuesSetToTrueCreatesFile() throws IOException {
        File file = new File("./testConfigs/config.yml");
        EasyConfig config = new EasyConfig(file, true);
        Assert.assertTrue("Should exist!", file.exists());
    }

    @Test
    public void testConstructingWithStringAndLoadAllValuesSetToTrueCreatesFile() throws IOException {
        File file = new File("./testConfigs/config.yml");
        EasyConfig config = new EasyConfig("./testConfigs/config.yml", true);
        Assert.assertTrue("Should exist!", file.exists());
    }

    @Test
    public void testConstructingWithFileObjectCreatesFile() throws IOException {
        File file = new File("./testConfigs/config.yml");
        EasyConfig config = new EasyConfig(file);
        Assert.assertTrue("Should exist!", file.exists());
    }

    @Test
    public void testConstructingWithStringCreatesFile() throws IOException {
        File file = new File("./testConfigs/config.yml");
        EasyConfig config = new EasyConfig("./testConfigs/config.yml");
        Assert.assertTrue("Should exist!", file.exists());
    }

    @After
    public void deleteConfig() {
        File fileToDelete = new File("./testConfigs/config.yml");
        fileToDelete.delete();
        File folderToDelete = new File("./testConfigs");
        folderToDelete.delete();
    }
}
