import com.wasykes.EasyConfig.Util;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedHashSet;
import java.util.Set;

public class PathListTest {
    @Test
    public void TestBuildPathList() {
        Set<String> paths = new LinkedHashSet<>();
        paths.add("Test 1");
        paths.add("Test 2");
        paths.add("Test 5");

        String message = Util.buildPathListMessage(paths);

        System.out.println(message);
        Assert.assertEquals("Message should be as written", "§6:§3--§1========================§3--§6:\n" +
                "§6:§3--§6: §1Test 1\n" +
                "§6:§3--§6: §1Test 2\n" +
                "§6:§3--§6: §1Test 5\n" +
                "§6:§3--§1========================§3--§6:", message);

    }
}
