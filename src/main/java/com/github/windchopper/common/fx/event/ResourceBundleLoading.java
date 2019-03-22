package com.github.windchopper.common.fx.event;

import java.util.ResourceBundle;

public class ResourceBundleLoading {

    private final ResourceBundle resources;

    public ResourceBundleLoading(ResourceBundle resources) {
        this.resources = resources;
    }

    public ResourceBundle resources() {
        return resources;
    }

}
