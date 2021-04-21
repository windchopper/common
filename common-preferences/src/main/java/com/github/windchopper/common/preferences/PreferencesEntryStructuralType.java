package com.github.windchopper.common.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PreferencesEntryStructuralType<T> extends PreferencesEntryType<T, Map<String, ?>> {

    private final Map<String, PreferencesEntryType<?, ?>> structure;
    private final Function<Map<String, ?>, T> decoder;
    private final Function<T, Map<String, ?>> encoder;

    public PreferencesEntryStructuralType(Map<String, PreferencesEntryType<?, ?>> structure, Function<Map<String, ?>, T> decoder, Function<T, Map<String, ?>> encoder) {
        this.structure = structure;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override protected T decode(Map<String, ?> storageValue) {
        return storageValue == null || storageValue.isEmpty() ? null : decoder.apply(storageValue);
    }

    @Override @SuppressWarnings({ "rawtypes", "unchecked" }) protected Map<String, Object> loadValue(PreferencesStorage storage, String name) throws Throwable {
        var storageValue = new HashMap<String, Object>();

        for (var entry : structure.entrySet()) {
            var entryType = (PreferencesEntryType) entry.getValue();
            storageValue.put(entry.getKey(), entryType.decode(entryType.loadValue(storage, entry.getKey())));
        }

        return storageValue;
    }

    @Override protected Map<String, ?> encode(T value) {
        return value == null ? null : encoder.apply(value);
    }

    @Override @SuppressWarnings({ "rawtypes", "unchecked" }) protected void saveValue(PreferencesStorage storage, String name, Map<String, ?> storageValue) throws Throwable {
        if (storageValue == null || storageValue.isEmpty()) {
            return;
        }

        for (var entry : structure.entrySet()) {
            var entryType = (PreferencesEntryType) entry.getValue();
            entryType.saveValue(storage, entry.getKey(), entryType.encode(storageValue.get(entry.getKey())));
        }
    }

}
