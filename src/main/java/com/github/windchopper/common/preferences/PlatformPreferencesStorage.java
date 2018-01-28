package com.github.windchopper.common.preferences;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

public class PlatformPreferencesStorage extends AbstractPreferencesStorage {

    private final Preferences preferences;

    public PlatformPreferencesStorage(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override public String value(String name, String defaultValue) {
        return preferences.get(name, defaultValue);
    }

    @Override public void putValue(String name, String value) {
        preferences.put(name, value);
    }

    @Override public void removeValue(String name) {
        preferences.remove(name);
    }

    @Override public Set<String> valueNames() {
        return invoke(
            () -> Arrays.stream(preferences.keys())
                .collect(Collectors.toSet()),
            this::logError,
            Collections::emptySet);
    }

    @Override public Set<String> childNames() {
        return invoke(
            () -> Arrays.stream(preferences.childrenNames())
                .collect(Collectors.toSet()),
            this::logError,
            Collections::emptySet);
    }

    @Override public PreferencesStorage child(String name) {
        return invoke(
            () -> new PlatformPreferencesStorage(preferences.node(name)),
            this::logError,
            () -> null);
    }

    @Override public void removeChild(String name) {
        invoke(
            () -> {
                preferences.node(name).removeNode();
                return null;
            },
            this::logError,
            () -> null);
    }

}
