package com.github.windchopper.common.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

    @Override protected T decode(Map<String, ?> storageValue) throws Exception {
        return decoder.apply(storageValue);
    }

    @Override protected Map<String, Object> loadInternal(PreferencesStorage storage) throws Exception {
        var storageValue = new HashMap<String, Object>();

        for (Entry<String, PreferencesEntryType<?, ?>> entry : structure.entrySet()) {
            storageValue.put(entry.getKey(), entry.getValue().loadInternal(storage, entry.getKey()));
        }

        return storageValue;
    }

    @Override protected Map<String, ?> encode(T value) throws Exception {
        return encoder.apply(value);
    }

    @Override @SuppressWarnings({ "rawtypes", "unchecked" }) protected void saveInternal(PreferencesStorage storage, Map<String, ?> storageValue) throws Exception {
        for (Entry<String, PreferencesEntryType<?, ?>> entry : structure.entrySet()) {
            ((PreferencesEntryType) entry.getValue()).saveInternal(storage, storageValue.get(entry.getKey()));
        }
    }

}
