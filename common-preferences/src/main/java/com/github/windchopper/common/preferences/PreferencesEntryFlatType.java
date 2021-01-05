package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.stream.FailableFunction;

public class PreferencesEntryFlatType<T> extends PreferencesEntryType<T, String> {

    private final FailableFunction<String, T, ? extends Exception> decoder;
    private final FailableFunction<T, String, ? extends Exception> encoder;

    public PreferencesEntryFlatType(FailableFunction<String, T, ? extends Exception> decoder, FailableFunction<T, String, ? extends Exception> encoder) {
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override protected T decode(String storageValue) throws Exception {
        return storageValue == null || storageValue.isBlank() ? null : decoder.apply(storageValue);
    }

    @Override protected String loadValue(PreferencesStorage storage, String name) throws Exception {
        return storage.value(name);
    }

    @Override protected String encode(T value) throws Exception {
        return value == null ? null : encoder.apply(value);
    }

    @Override protected void saveValue(PreferencesStorage storage, String name, String storageValue) throws Exception {
        if (storageValue == null || storageValue.isBlank()) {
            return;
        }

        storage.saveValue(name, storageValue);
    }

}
