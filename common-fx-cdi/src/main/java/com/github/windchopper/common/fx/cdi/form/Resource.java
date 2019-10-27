package com.github.windchopper.common.fx.cdi.form;

import java.io.InputStream;
import java.net.URL;

public class Resource {

    private final String path;

    public Resource(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

    public URL url() {
        return getClass().getClassLoader().getResource(path);
    }

    public InputStream stream() {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

}
