package com.wasykes.EasyConfig;

/**
 * TODO: Add documentation
 */
public abstract class ConfigComponent {

    protected EasyConfig componentConfig;

    protected ConfigComponent(EasyConfig componentConfig) {
        this.componentConfig = componentConfig;
    }

    public void setComponentConfig(EasyConfig componentConfig) {
        this.componentConfig = componentConfig;
    }

    public EasyConfig getComponentConfig() {
        return componentConfig;
    }

}
