package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.PreferencesEntryType;
import com.github.windchopper.common.preferences.PreferencesStorage;

import java.util.Optional;
import java.util.function.Function;

public class FlatType<T> implements PreferencesEntryType<T> {

    private final Function<String, T> transformer;
    private final Function<T, String> reverseTransformer;

    public FlatType(Function<String, T> transformer, Function<T, String> reverseTransformer) {
        this.transformer = transformer;
        this.reverseTransformer = reverseTransformer;
    }

    @Override public T load(PreferencesStorage storage, String name) {
        return Optional.ofNullable(storage.value(name, null))
            .map(transformer).orElse(null);
    }

    @Override public void save(PreferencesStorage storage, String name, T value) {
        String valueToStore = Optional.ofNullable(value)
            .map(reverseTransformer).orElse(null);

        if (valueToStore != null) {
            storage.putValue(name, valueToStore);
        } else {
            storage.removeValue(name);
        }
    }

}
