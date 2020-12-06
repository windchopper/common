package com.github.windchopper.common.preferences;

public interface PreferencesEntryType<T> {

    T load(PreferencesStorage storage, String name);
    void save(PreferencesStorage storage, String name, T value);

}
