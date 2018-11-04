import com.wasykes.EasyConfig.EasyConfig;
import mocks.MockComponent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class ComponentSystemTest {

    @Test
    public void testIfComponentUpdates() throws IOException {
        EasyConfig config = new EasyConfig("./testConfigs/testComponents.yml");
        MockComponent component = new MockComponent(config);
        config.setValue("This.is.a.test", 1);
        Assert.assertTrue("Should be true", component.getConfig().isLoadedToMemory("This.is.a.test"));
    }

    @After
    public void deleteConfig() {
        File fileToDelete = new File("./testConfigs/testComponents.yml");
        fileToDelete.delete();
        File folderToDelete = new File("./testConfigs");
        folderToDelete.delete();
    }
}
