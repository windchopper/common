package com.github.windchopper.common.preferences;

import java.util.Optional;
import java.util.Set;

public class PreferencesStorageDecorator implements PreferencesStorage {

    protected final PreferencesStorage storage;

    protected PreferencesStorageDecorator(PreferencesStorage storage) {
        this.storage = storage;
    }

    @Override public Optional<PreferencesEntryText> value(String name) {
        return storage.value(name);
    }

    @Override public void saveValue(String name, String text) {
        storage.saveValue(name, text);
    }

    @Override
    public void removeValue(String name) {
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
