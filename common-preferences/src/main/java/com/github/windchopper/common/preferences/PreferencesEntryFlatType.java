package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.stream.FallibleFunction;

public class PreferencesEntryFlatType<T> extends PreferencesEntryType<T, String> {

    private final FallibleFunction<String, T> decoder;
    private final FallibleFunction<T, String> encoder;

    public PreferencesEntryFlatType(FallibleFunction<String, T> decoder, FallibleFunction<T, String> encoder) {
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override protected T decode(String storageValue) throws Throwable {
        return storageValue == null || storageValue.isBlank() ? null : decoder.apply(storageValue);
    }

    @Override protected String loadValue(PreferencesStorage storage, String name) throws Exception {
        return storage.value(name);
    }

    @Override protected String encode(T value) throws Throwable {
        return value == null ? null : encoder.apply(value);
    }

    @Override protected void saveValue(PreferencesStorage storage, String name, String storageValue) throws Exception {
        if (storageValue == null || storageValue.isBlank()) {
            return;
        }

        storage.saveValue(name, storageValue);
    }

}
