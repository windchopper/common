package com.github.windchopper.common.preferences;

public interface PreferencesEntry<T> {

    PreferencesEntryValueHolder<T> load() throws PreferencesException;
    void save(T value) throws PreferencesException;

}
