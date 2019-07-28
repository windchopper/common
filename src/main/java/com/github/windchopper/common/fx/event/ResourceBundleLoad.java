package com.github.windchopper.common.fx.event;

import java.util.ResourceBundle;

public class ResourceBundleLoad {

    private final ResourceBundle resources;

    public ResourceBundleLoad(ResourceBundle resources) {
        this.resources = resources;
    }

    public ResourceBundle resources() {
        return resources;
    }

}
