package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.preferences.*;

import java.util.Optional;
import java.util.function.Function;

import static java.util.function.Predicate.not;

public class FlatType<T> implements PreferencesEntryType<T> {

    protected final Function<String, T> transformer;
    protected final Function<T, String> reverseTransformer;

    public FlatType(Function<String, T> transformer, Function<T, String> reverseTransformer) {
        this.transformer = transformer;
        this.reverseTransformer = reverseTransformer;
    }

    @Override public T load(PreferencesStorage storage, String name) {
        return storage.value(name)
            .map(PreferencesEntryText::text)
            .filter(not(String::isBlank))
            .map(transformer)
            .orElse(null);
    }

    @Override public void save(PreferencesStorage storage, String name, T value) {
        storage.saveValue(name, Optional.ofNullable(value)
            .map(reverseTransformer)
            .orElse(null));
    }

}
