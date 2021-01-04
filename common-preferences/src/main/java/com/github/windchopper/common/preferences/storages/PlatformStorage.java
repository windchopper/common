package com.github.windchopper.common.preferences.storages;

import com.github.windchopper.common.preferences.PreferencesStorage;

import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PlatformStorage implements PreferencesStorage {

    private final Preferences preferences;

    public PlatformStorage(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override public String value(String name) {
        return preferences.get(name, null);
    }

    @Override public void saveValue(String name, String text) {
        preferences.put(name, text);
    }

    @Override public void dropValue(String name) {
        preferences.remove(name);
    }

    @Override public Set<String> valueNames() throws BackingStoreException {
        return Set.of(preferences.keys());
    }

    @Override public Set<String> childNames() throws BackingStoreException {
        return Set.of(preferences.childrenNames());
    }

    @Override public PreferencesStorage child(String name) {
        return new PlatformStorage(preferences.node(name));
    }

    @Override public void dropChild(String name) throws BackingStoreException {
        preferences.node(name).removeNode();
    }

}
