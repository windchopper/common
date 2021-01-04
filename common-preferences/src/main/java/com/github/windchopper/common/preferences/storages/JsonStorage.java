package com.github.windchopper.common.preferences.storages;

import com.github.windchopper.common.preferences.PreferencesStorage;
import com.github.windchopper.common.util.Resource;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public class JsonStorage implements PreferencesStorage {

    private final static JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

    private final Instant timestamp;
    private final JsonObject storageObject;

    public JsonStorage(Resource resource) throws IOException {
        var connection = resource.url()
            .openConnection();

        timestamp = Instant.ofEpochMilli(connection.getLastModified());

        try (InputStream stream = connection.getInputStream(); JsonReader reader = Json.createReader(stream)) {
            storageObject = reader.readObject();
        }
    }

    public JsonStorage(Instant timestamp, JsonObject storageObject) {
        this.timestamp = timestamp;
        this.storageObject = storageObject;
    }

    @Override public String value(String name) {
        return Optional.ofNullable(storageObject.getJsonString(name))
            .map(JsonString::getString)
            .orElse(null);
    }

    @Override public void saveValue(String name, String text) {
        storageObject.put(name, Json.createValue(text));
    }

    @Override public void dropValue(String name) {
        storageObject.remove(name);
    }

    @Override public PreferencesStorage child(String name) {
        var child = storageObject.getJsonObject(name);

        if (child == null) {
            storageObject.put(name, child = jsonObjectBuilder.build());
        }

        return new JsonStorage(timestamp, child);
    }

    @Override public void dropChild(String name) {
        storageObject.remove(name);
    }

    @Override public Set<String> valueNames() {
        return storageObject.keySet().stream()
            .filter(not(key -> storageObject.get(key) instanceof JsonStructure))
            .collect(toSet());
    }

    @Override public Set<String> childNames() {
        return storageObject.keySet().stream()
            .filter(key -> storageObject.get(key) instanceof JsonStructure)
            .collect(toSet());
    }

    @Override public Optional<Instant> timestamp() {
        return Optional.ofNullable(timestamp);
    }

}
