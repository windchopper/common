package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.stream.FailableRunnable;
import com.github.windchopper.common.util.stream.FailableSupplier;

import java.util.Collections;
import java.util.Set;
import java.util.prefs.Preferences;

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
        return FailableSupplier.failsafeGet(preferences::keys)
            .onFailure(this::logError)
            .result()
            .map(Set::of)
            .orElseGet(Collections::emptySet);
    }

    @Override public Set<String> childNames() {
        return FailableSupplier.failsafeGet(preferences::childrenNames)
            .onFailure(this::logError)
            .result()
            .map(Set::of)
            .orElseGet(Collections::emptySet);
    }

    @Override public PreferencesStorage child(String name) {
        return FailableSupplier.failsafeGet(() -> new PlatformPreferencesStorage(preferences.node(name)))
            .onFailure(this::logError)
            .result()
            .orElse(null);
    }

    @Override public void removeChild(String name) {
        FailableRunnable.failsafeRun(() -> preferences.node(name).removeNode())
            .onFailure(this::logError);
    }

}
