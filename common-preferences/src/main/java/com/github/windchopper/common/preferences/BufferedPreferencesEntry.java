package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.BufferedReference;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public class BufferedPreferencesEntry<T> extends PreferencesEntry<T> {

    private final BufferedReference<T, RuntimeException> storedValue;

    public BufferedPreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type, Duration bufferLifetime) {
        super(storage, name, type);
        storedValue = new BufferedReference<>(
            requireNonNull(bufferLifetime, "bufferLifetime"),
            super::load);
    }

    @Override public T load() {
        return storedValue.get();
    }

    @Override public void save(T value) {
        super.save(value);
        storedValue.invalidate();
    }

}
