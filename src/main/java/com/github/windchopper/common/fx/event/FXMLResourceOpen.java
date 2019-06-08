package com.github.windchopper.common.fx.event;

import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public class FXMLResourceOpen {

    private final Stage stage;
    private final String resource;
    private final Map<String, ?> parameters;

    public FXMLResourceOpen(Stage stage, String resource) {
        this(stage, resource, emptyMap());
    }

    public FXMLResourceOpen(Stage stage, String resource, Map<String, ?> parameters) {
        this.stage = stage;
        this.resource = resource;
        this.parameters = parameters;
    }

    public Stage stage() {
        return stage;
    }

    public String resource() {
        return resource;
    }

    public InputStream resourceAsStream() {
        return getClass().getClassLoader().getResourceAsStream(resource);
    }

    public Map<String, ?> parameters() {
        return parameters;
    }

    public <T> T findParameter(String name, Class<? extends T> type) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
        return Optional.ofNullable(parameters.get(name))
            .filter(type::isInstance)
            .map(type::cast)
            .orElse(null);
    }

}