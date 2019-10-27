package com.github.windchopper.common.fx.cdi.form;

import javafx.collections.ObservableMap;
import javafx.scene.Parent;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public class FormLoad {

    private final String resource;
    private final Map<String, ?> parameters;

    public FormLoad(String resource) {
        this(resource, emptyMap());
    }

    public FormLoad(String resource, Map<String, ?> parameters) {
        this.resource = resource;
        this.parameters = parameters;
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

    public void afterLoad(Parent form, Object controller, Map<String, ?> parameters, ObservableMap<String, ?> formNamespace) {
        if (controller instanceof FormController) {
            ((FormController) controller).afterLoad(form, parameters, formNamespace);
        }
    }

}
