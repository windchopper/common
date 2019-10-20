package com.github.windchopper.common.preferences;

import java.util.Set;

public class PreferencesStorageDecorator implements PreferencesStorage {

    protected final PreferencesStorage storage;

    protected PreferencesStorageDecorator(PreferencesStorage storage) {
        this.storage = storage;
    }

    @Override public String value(String name, String defaultValue) {
        return storage.value(name, defaultValue);
    }

    @Override public void putValue(String name, String value) {
        storage.putValue(name, value);
    }

    @Override public void removeValue(String name) {
        storage.removeValue(name);
    }

    @Override public Set<String> valueNames() {
        return storage.valueNames();
    }

    @Override public Set<String> childNames() {
        return storage.childNames();
    }

    @Override public PreferencesStorage child(String name) {
        return storage.child(name);
    }

    @Override public void removeChild(String name) {
        storage.removeChild(name);
    }

}
