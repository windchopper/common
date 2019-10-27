package com.github.windchopper.common.util;

import java.io.InputStream;
import java.net.URL;

public abstract class Resource {

    protected final String path;

    protected Resource(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

    public abstract URL url();
    public abstract InputStream stream();

}
