package com.wasykes.EasyConfig;

/**
 *
 * ConfigComponent super class
 *
 * @author Darknuju
 * @version 1.0
 * @since 10/30/2018
 *
 */
public abstract class ConfigComponent {

    protected EasyConfig componentConfig;
    public final String componentLabel;

    protected ConfigComponent(EasyConfig componentConfig, String label) {
        this.componentConfig = componentConfig;
        this.componentConfig.addConfigComponent(this);
        this.componentLabel = label;
    }

    public void setComponentConfig(EasyConfig componentConfig) {
        this.componentConfig = componentConfig;
    }

    public EasyConfig getComponentConfig() {
        return componentConfig;
    }

}
