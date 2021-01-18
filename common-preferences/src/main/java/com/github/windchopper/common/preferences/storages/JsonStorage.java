package com.github.windchopper.common.preferences.storages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.windchopper.common.preferences.PreferencesStorage;
import com.github.windchopper.common.util.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public class JsonStorage implements PreferencesStorage {

    private final Instant timestamp;
    private final Map<String, Object> rootObject;
    private final ObjectMapper mapper;

    public JsonStorage(Resource resource) throws IOException {
        this(resource, new ObjectMapper());
    }

    public JsonStorage(Resource resource, ObjectMapper mapper) throws IOException {
        var connection = resource.url()
            .openConnection();

        timestamp = Instant.ofEpochMilli(connection.getLastModified());

        this.mapper = mapper;

        var reader = mapper.readerFor(mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));

        try (InputStream stream = connection.getInputStream()) {
            rootObject = reader.readValue(stream);
        }
    }

    public JsonStorage(Instant timestamp, Map<String, Object> rootObject) {
        this(timestamp, rootObject, new ObjectMapper());
    }

    public JsonStorage(Instant timestamp, Map<String, Object> rootObject, ObjectMapper mapper) {
        this.timestamp = timestamp;
        this.rootObject = rootObject;
        this.mapper = mapper;
    }

    @Override public String value(String name) {
        return Optional.ofNullable(rootObject.get(name))
            .map(Objects::toString)
            .orElse(null);
    }

    @Override public void saveValue(String name, String text) {
        rootObject.put(name, text);
    }

    @Override public void dropValue(String name) {
        rootObject.remove(name);
    }

    @Override @SuppressWarnings("unchecked") public PreferencesStorage child(String name) {
        return new JsonStorage(timestamp, (Map<String, Object>) rootObject.computeIfAbsent(name, missingName -> new HashMap<>()), mapper);
    }

    @Override public void dropChild(String name) {
        rootObject.remove(name);
    }

    @Override public Set<String> valueNames() {
        return rootObject.keySet().stream()
            .filter(not(key -> rootObject.get(key) instanceof Map))
            .collect(toSet());
    }

    @Override public Set<String> childNames() {
        return rootObject.keySet().stream()
            .filter(key -> rootObject.get(key) instanceof Map)
            .collect(toSet());
    }

    @Override public Optional<Instant> timestamp() {
        return Optional.ofNullable(timestamp);
    }

}
