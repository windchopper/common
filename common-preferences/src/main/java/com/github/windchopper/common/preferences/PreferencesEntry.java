package com.github.windchopper.common.preferences;

import static java.util.Objects.requireNonNull;

public class PreferencesEntry<T> {

    private final PreferencesStorage storage;
    private final String name;
    private final PreferencesEntryType<T> type;

    public PreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type) {
        this.storage = requireNonNull(storage, "storage");
        this.name = requireNonNull(name, "name");
        this.type = requireNonNull(type, "type");
    }

    public T load() {
        return type.load(storage, name);
    }

    public void save(T value) {
        type.save(storage, name, value);
    }

}
