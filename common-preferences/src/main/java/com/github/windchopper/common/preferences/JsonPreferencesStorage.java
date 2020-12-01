package com.github.windchopper.common.preferences;

import javax.json.*;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public class JsonPreferencesStorage extends AbstractPreferencesStorage {

    private final static JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

    private final JsonObject jsonObject;

    public JsonPreferencesStorage(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override public String value(String name, String defaultValue) {
        return Optional.ofNullable(jsonObject.getString(name))
            .orElse(defaultValue);
    }

    @Override public void putValue(String name, String value) {
        jsonObject.put(name, Json.createValue(value));
    }

    @Override public void removeValue(String name) {
        jsonObject.remove(name);
    }

    @Override public Set<String> valueNames() {
        return jsonObject.keySet().stream()
            .filter(not(key -> jsonObject.get(key) instanceof JsonStructure))
            .collect(toSet());
    }

    @Override public Set<String> childNames() {
        return jsonObject.keySet().stream()
            .filter(key -> jsonObject.get(key) instanceof JsonStructure)
            .collect(toSet());
    }

    @Override public PreferencesStorage child(String name) {
        var child = jsonObject.getJsonObject(name);

        if (child == null) {
            jsonObject.put(name, child = jsonObjectBuilder.build());
        }

        return new JsonPreferencesStorage(child);
    }

    @Override public void removeChild(String name) {
        jsonObject.remove(name);
    }

}
