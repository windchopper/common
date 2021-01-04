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
        return decoder.apply(storageValue);
    }

    @Override protected String loadInternal(PreferencesStorage storage) throws Exception {
        return storage.value("value");
    }

    @Override protected String encode(T value) throws Exception {
        return encoder.apply(value);
    }

    @Override protected void saveInternal(PreferencesStorage storage, String storageValue) throws Exception {
        storage.saveValue("value", storageValue);
    }

}
