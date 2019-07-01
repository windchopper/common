package com.github.windchopper.common.fx.event;

import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Collections.emptyMap;

public class FXMLResourceOpen {

    private final Supplier<Stage> stageSupplier;
    private final String resource;
    private final Map<String, ?> parameters;

    public FXMLResourceOpen(Stage stage, String resource) {
        this(() -> stage, resource, emptyMap());
    }

    public FXMLResourceOpen(Supplier<Stage> stageSupplier, String resource) {
        this(stageSupplier, resource, emptyMap());
    }

    public FXMLResourceOpen(Stage stage, String resource, Map<String, ?> parameters) {
        this(() -> stage, resource, parameters);
    }

    public FXMLResourceOpen(Supplier<Stage> stageSupplier, String resource, Map<String, ?> parameters) {
        this.stageSupplier = stageSupplier;
        this.resource = resource;
        this.parameters = parameters;
    }

    public Stage stage() {
        return stageSupplier.get();
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
        return Optional.ofNullable(parameters.get(Objects.requireNonNull(name)))
            .filter(Objects.requireNonNull(type)::isInstance)
            .map(type::cast)
            .orElse(null);
    }

}
