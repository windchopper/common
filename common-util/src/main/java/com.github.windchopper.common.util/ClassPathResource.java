package com.github.windchopper.common.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class ClassPathResource extends Resource {

    public ClassPathResource(String path) {
        super(path);
    }

    @Override
    public URL url() {
        return Optional.ofNullable(getClass().getResource(path))
            .orElseGet(() -> getClass().getClassLoader().getResource(path));
    }

    @Override
    public InputStream stream() {
        return Optional.ofNullable(getClass().getResourceAsStream(path))
            .orElseGet(() -> getClass().getClassLoader().getResourceAsStream(path));
    }

}
