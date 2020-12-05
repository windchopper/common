package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryType;
import com.github.windchopper.common.preferences.PreferencesStorage;

import javax.json.*;
import java.util.Optional;
import java.util.function.Function;

public class StructuralType<T> implements PreferencesEntryType<T> {

    protected final Function<JsonObject, T> transformer;
    protected final Function<T, JsonObject> reverseTransformer;

    public StructuralType(Function<JsonObject, T> transformer, Function<T, JsonObject> reverseTransformer) {
        this.transformer = transformer;
        this.reverseTransformer = reverseTransformer;
    }

    @Override public T load(PreferencesStorage storage, String name) {
        return Optional.ofNullable(load(storage.child(name)).build())
            .map(transformer)
            .orElse(null);
    }

    private JsonObjectBuilder load(PreferencesStorage storage) {
        var jsonObjectBuilder = Json.createObjectBuilder();

        for (String valueName : storage.valueNames()) {
            jsonObjectBuilder.add(valueName, storage.value(valueName, null));
        }

        for (String childName : storage.childNames()) {
            jsonObjectBuilder.add(childName, load(storage.child(childName)));
        }

        return jsonObjectBuilder;
    }

    @Override public void save(PreferencesStorage storage, String name, T value) {
        save(storage.child(name), Optional.ofNullable(value)
            .map(reverseTransformer)
            .orElse(null));
    }

    private void save(PreferencesStorage storage, JsonObject jsonObject) {
        for (var valueKey : storage.valueNames()) {
            storage.removeValue(valueKey);
        }

        for (var childName : storage.childNames()) {
            storage.removeChild(childName);
        }

        if (jsonObject != null)
            for (var entry : jsonObject.entrySet())
                switch (entry.getValue().getValueType()) {
                    case OBJECT:
                        save(storage.child(entry.getKey()), entry.getValue().asJsonObject());
                        break;

                    case STRING:
                        storage.putValue(entry.getKey(), ((JsonString) entry.getValue()).getString());
                        break;

                    default:
                        storage.putValue(entry.getKey(), entry.getValue().toString());
                }
    }

}
