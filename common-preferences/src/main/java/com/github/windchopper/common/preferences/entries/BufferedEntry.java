package com.github.windchopper.common.preferences.entries;

import com.github.windchopper.common.preferences.*;
import com.github.windchopper.common.util.BufferedReference;

import java.time.Duration;

public class BufferedEntry<T> implements PreferencesEntry<T> {

    private final PreferencesEntry<T> entry;
    private final BufferedReference<PreferencesEntryValueHolder<T>, RuntimeException> valueHolder;

    public BufferedEntry(Duration bufferLifetime, PreferencesEntry<T> entry) {
        valueHolder = new BufferedReference<>(bufferLifetime, entry::load);
        this.entry = entry;
    }

    @Override public PreferencesEntryValueHolder<T> load() throws PreferencesException {
        return valueHolder.get();
    }

    @Override public void save(T value) throws PreferencesException {
        entry.save(value);
        valueHolder.invalidate();
    }

}
