import com.wasykes.EasyConfig.EasyConfig;
import com.wasykes.EasyConfig.components.BackupComponent;
import com.wasykes.EasyConfig.components.LiveEditCommandComponent;
import mocks.MockCommand;
import mocks.MockSender;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CommandComponentTest {

    private EasyConfig config;
    private LiveEditCommandComponent command;
    private BackupComponent backup;
    private MockSender mockSender;
    private MockCommand mockCommand;

    @Before
    public void initConfig() throws IOException {
        config = new EasyConfig("./testConfigs/testConfig.yml", false, false);
        Assert.assertNotNull("Config should not be null!", config);
        config.setValue("Test1", 3);
        config.setValue("Test2", "Success!");
        config.setValue("Test3", Material.APPLE);
        command = new LiveEditCommandComponent(config, "test");
        Arrays.stream(LiveEditCommandComponent.ConfigCommand.values()).forEach((cmd) -> command.addCommand(cmd));
        backup = new BackupComponent(config);
        command.addComponent(backup);
        mockSender = new MockSender();
        mockCommand = new MockCommand();
    }

    @Test
    public void testPathListListsAllElements() {
        String[] args = new String[1];
        args[0] = "list";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertTrue("Message should contain Test3!", (mockSender.message.contains("Test1")
                && mockSender.message.contains("Test2")
                && mockSender.message.contains("Test3")));
    }

    @Test
    public void testValueGetGetsIntegerValue() {
        String[] args = new String[2];
        args[0] = "get";
        args[1] = "Test1";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertTrue("Message should contain 3", mockSender.message.contains("3"));
    }

    @Test
    public void testValueGetGetsStringValue() {
        String[] args = new String[2];
        args[0] = "get";
        args[1] = "Test2";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertTrue("Message should contain Success!", mockSender.message.contains("Success!"));
    }

    @Test
    public void testValueGetGetsEnumValue() {
        String[] args = new String[2];
        args[0] = "get";
        args[1] = "Test3";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertTrue("Message should contain APPLE", mockSender.message.contains("APPLE"));
    }

    @Test
    public void testValueSetSetsStringValue() {
        String[] args = new String[5];
        args[0] = "set";
        args[1] = "Test2";
        args[2] = "string";
        args[3] = "This";
        args[4] = "message";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertEquals("Value should equal: This message!", "This message", config.getValue("Test2"));
    }

    @Test
    public void testBackupCommandWorks() throws IOException {
        config.getRawConfigFile().getParentFile().mkdir();
        config.getRawConfigFile().createNewFile();
        String[] args = new String[1];
        args[0] = "backup";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertTrue("Should be true!", new File("./testConfigs/testConfig-backup.yml").delete());
    }

    @Test
    public void testBackupRevertCommandWorks() throws IOException {
        config.getRawConfigFile().getParentFile().mkdir();
        config.getRawConfigFile().createNewFile();
        config.setValue("Test1", "test1");
        config.unloadConfigurationFromMemory("Test1");
        backup.backup();
        config.setValue("Test1", "test2");
        config.unloadConfigurationFromMemory("Test1");
        String[] args = new String[1];
        args[0] = "revert";
        command.onCommand(mockSender, mockCommand, "test", args);
        Assert.assertEquals("Should equal test1!", "test1", YamlConfiguration.loadConfiguration(config.getRawConfigFile()).getString("Test1"));
    }

    @After
    public void deleteAllFiles() {
        new File("./testConfigs/testConfig-backup.yml").delete();
        new File("./testConfigs/testConfig.yml").delete();
        new File("./testConfigs").delete();
    }

}
