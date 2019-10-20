package com.github.windchopper.common.preferences;

import java.util.prefs.BackingStoreException;

public interface PreferencesEntryType<T> {

    T load(PreferencesStorage storage, String name) throws BackingStoreException;
    void save(PreferencesStorage storage, String name, T value) throws BackingStoreException;

}
