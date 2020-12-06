package com.github.windchopper.common.preferences;

import javax.json.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public class JsonPreferencesStorage extends AbstractPreferencesStorage {

    private final static JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

    private final LocalDateTime jsonFileModificationTime;
    private final JsonObject jsonObject;

    public JsonPreferencesStorage(JsonObject jsonObject) {
        this(LocalDateTime.MIN, jsonObject);
    }

    public JsonPreferencesStorage(LocalDateTime jsonFileModificationTime, JsonObject jsonObject) {
        this.jsonFileModificationTime = jsonFileModificationTime;
        this.jsonObject = jsonObject;
    }

    @Override public Optional<PreferencesEntryText> valueImpl(String name) {
        return Optional.ofNullable(jsonObject.getString(name))
            .map(encoded -> new PreferencesEntryText(jsonFileModificationTime).decodeFromString(encoded));
    }

    @Override public void saveValueImpl(String name, String text) {
        jsonObject.put(name, Json.createValue(new PreferencesEntryText(LocalDateTime.now(), text).encodeToString()));
    }

    @Override public void removeValueImpl(String name) {
        jsonObject.remove(name);
    }

    @Override public PreferencesStorage childImpl(String name) {
        var child = jsonObject.getJsonObject(name);

        if (child == null) {
            jsonObject.put(name, child = jsonObjectBuilder.build());
        }

        return new JsonPreferencesStorage(child);
    }

    @Override public void removeChildImpl(String name) {
        jsonObject.remove(name);
    }

    @Override public Set<String> valueNamesImpl() {
        return jsonObject.keySet().stream()
            .filter(not(key -> jsonObject.get(key) instanceof JsonStructure))
            .collect(toSet());
    }

    @Override public Set<String> childNamesImpl() {
        return jsonObject.keySet().stream()
            .filter(key -> jsonObject.get(key) instanceof JsonStructure)
            .collect(toSet());
    }

}
