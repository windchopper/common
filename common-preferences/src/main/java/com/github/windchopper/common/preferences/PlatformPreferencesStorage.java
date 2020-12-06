package com.github.windchopper.common.preferences;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PlatformPreferencesStorage extends AbstractPreferencesStorage {

    private final Preferences preferences;

    public PlatformPreferencesStorage(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override public Optional<PreferencesEntryText> valueImpl(String name) {
        return Optional.ofNullable(preferences.get(name, null))
            .map(encoded -> new PreferencesEntryText().decodeFromString(encoded));
    }

    @Override public void saveValueImpl(String name, String text) {
        preferences.put(name, new PreferencesEntryText(LocalDateTime.now(), text).encodeToString());
    }

    @Override public void removeValueImpl(String name) {
        preferences.remove(name);
    }

    @Override public Set<String> valueNamesImpl() throws BackingStoreException {
        return Set.of(preferences.keys());
    }

    @Override public Set<String> childNamesImpl() throws BackingStoreException {
        return Set.of(preferences.childrenNames());
    }

    @Override public PreferencesStorage childImpl(String name) {
        return new PlatformPreferencesStorage(preferences.node(name));
    }

    @Override public void removeChildImpl(String name) throws BackingStoreException {
        preferences.node(name).removeNode();
    }

}
