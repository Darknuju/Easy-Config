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
