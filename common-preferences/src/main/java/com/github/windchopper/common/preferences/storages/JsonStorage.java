package com.github.windchopper.common.preferences.storages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.windchopper.common.preferences.PreferencesStorage;
import com.github.windchopper.common.util.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public class JsonStorage implements PreferencesStorage {

    public static Supplier<ObjectMapper> objectMapperFactory;

    private final Instant timestamp;
    private final Map<String, Object> storageObject;

    public JsonStorage(Resource resource) throws IOException {
        var connection = resource.url()
            .openConnection();

        timestamp = Instant.ofEpochMilli(connection.getLastModified());

        ObjectMapper objectMapper = objectMapperFactory.get();
        ObjectReader reader = objectMapper.readerFor(objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));

        try (InputStream stream = connection.getInputStream()) {
            storageObject = reader.readValue(stream);
        }
    }

    public JsonStorage(Instant timestamp, Map<String, Object> storageObject) {
        this.timestamp = timestamp;
        this.storageObject = storageObject;
    }

    @Override public String value(String name) {
        return Optional.ofNullable(storageObject.get(name))
            .map(Objects::toString)
            .orElse(null);
    }

    @Override public void saveValue(String name, String text) {
        storageObject.put(name, text);
    }

    @Override public void dropValue(String name) {
        storageObject.remove(name);
    }

    @Override @SuppressWarnings("unchecked") public PreferencesStorage child(String name) {
        return new JsonStorage(timestamp, (Map<String, Object>) storageObject.computeIfAbsent(name, missingName -> new HashMap<>()));
    }

    @Override public void dropChild(String name) {
        storageObject.remove(name);
    }

    @Override public Set<String> valueNames() {
        return storageObject.keySet().stream()
            .filter(not(key -> storageObject.get(key) instanceof Map))
            .collect(toSet());
    }

    @Override public Set<String> childNames() {
        return storageObject.keySet().stream()
            .filter(key -> storageObject.get(key) instanceof Map)
            .collect(toSet());
    }

    @Override public Optional<Instant> timestamp() {
        return Optional.ofNullable(timestamp);
    }

}
