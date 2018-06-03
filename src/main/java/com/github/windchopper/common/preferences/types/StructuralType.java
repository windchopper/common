package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryType;
import com.github.windchopper.common.preferences.PreferencesStorage;

import javax.json.*;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class StructuralType<T> implements PreferencesEntryType<T> {

    final Function<JsonObject, T> transformer;
    final Function<T, JsonObject> reverseTransformer;

    public StructuralType(Function<JsonObject, T> transformer, Function<T, JsonObject> reverseTransformer) {
        this.transformer = transformer;
        this.reverseTransformer = reverseTransformer;
    }

    @Override public T load(PreferencesStorage storage, String name) {
        return Optional.ofNullable(load(storage.child(name)).build())
            .map(transformer).orElse(null);
    }

    private JsonObjectBuilder load(PreferencesStorage storage) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

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
            .map(reverseTransformer).orElse(null));
    }

    private void save(PreferencesStorage storage, JsonObject jsonObject) {
        for (String valueKey : storage.valueNames()) {
            storage.removeValue(valueKey);
        }

        for (String childName : storage.childNames()) {
            storage.removeChild(childName);
        }

        if (jsonObject != null)
            for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                JsonValue value = entry.getValue();

                switch (value.getValueType()) {
                    case STRING:
                        storage.putValue(key,
                            JsonString.class.cast(value).getString());
                        break;

                    case OBJECT:
                        save(storage.child(key),
                            JsonObject.class.cast(value));
                        break;

                    default:
                }
            }
    }

}
