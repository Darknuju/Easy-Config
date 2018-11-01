import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.command.LiveEditCommand;
import mocks.MockCommand;
import mocks.MockSender;
import org.bukkit.Material;
import org.junit.*;
import java.io.File;
import java.io.IOException;

public class CommandComponentTest {

    EasyConfig config;
    LiveEditCommand command;
    MockSender mockSender;
    MockCommand mockCommand;

    @Before
    public void initConfig() {
        try {
            config = new EasyConfig("./testConfigs/testConfig.yml");
        } catch(IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull("Config should not be null!", config);
        config.setValue("Test1", 3);
        config.setValue("Test2", "Success!");
        config.setValue("Test3", Material.APPLE);
        command = new LiveEditCommand(config, "test");
        mockSender = new MockSender();
        mockCommand = new MockCommand();
    }

    @Test
    public void TestPathList() {
        String[] args = new String[1];
        args[0] = "list";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertTrue("Message should contain :!", mockSender.message.contains(":"));
        Assert.assertTrue("Message should contain -!", mockSender.message.contains("-"));
        Assert.assertTrue("Message should contain =!", mockSender.message.contains("="));
        Assert.assertTrue("Message should contain Test1!", mockSender.message.contains("Test1"));
        Assert.assertTrue("Message should contain Test2!", mockSender.message.contains("Test2"));
        Assert.assertTrue("Message should contain Test3!", mockSender.message.contains("Test3"));
        System.out.println(mockSender.message);
    }

    @Test
    public void TestValueGet() {
        String[] args = new String[2];
        args[0] = "get";
        args[1] = "Test1";
        command.onCommand(mockSender, mockCommand, "test", args);
        System.out.println(mockSender.message);
        Assert.assertTrue("Message should contain 3", mockSender.message.contains("3"));
        args[1] = "Test2";
        command.onCommand(mockSender, mockCommand, "test", args);
        System.out.println(mockSender.message);
        Assert.assertTrue("Message should contain Success!", mockSender.message.contains("Success!"));
        args[1] = "Test3";
        command.onCommand(mockSender, mockCommand, "test", args);
        System.out.println(mockSender.message);
        Assert.assertTrue("Message should contain APPLE", mockSender.message.contains("APPLE"));
    }

    @Test
    public void TestValueSet() {
        String[] args = new String[4];
        args[0] = "set";
        args[1] = "Test2";
        args[2] = "This";
        args[3] = "message";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertEquals("Value should equal: This message!", "This message", config.getValue("Test2"));
        System.out.println(config.getValue("Test2"));
    }


    @After
    public void deleteConfig() {
        File fileToDelete = new File("./testConfigs/testConfig.yml");
        Assert.assertTrue("Should delete config file!", fileToDelete.delete());
        File folderToDelete = new File("./testConfigs");
        Assert.assertTrue("Should delete config folder!", folderToDelete.delete());
    }
}
