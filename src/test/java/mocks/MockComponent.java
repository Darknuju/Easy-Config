package mocks;

import com.wasykes.EasyConfig.ConfigComponent;
import com.wasykes.EasyConfig.EasyConfig;

public class MockComponent extends ConfigComponent {

    public MockComponent(EasyConfig config) {
        super(config, "mock");
    }

    public EasyConfig getConfig() {
        return this.componentConfig;
    }
}
