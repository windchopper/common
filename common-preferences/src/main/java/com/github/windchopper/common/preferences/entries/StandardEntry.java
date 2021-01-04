package com.github.windchopper.common.preferences.entries;

import com.github.windchopper.common.preferences.*;

import java.util.*;

public class StandardEntry<T> implements PreferencesEntry<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.preferences.i18n.messages");

    private final PreferencesStorage storage;
    private final String name;
    private final PreferencesEntryType<T, ?> type;

    public StandardEntry(PreferencesStorage storage, String name, PreferencesEntryType<T, ?> type) throws PreferencesException {
        if (name.startsWith(PreferencesStorage.SEPARATOR)) {
            throw new PreferencesException(bundle.getString("com.github.windchopper.common.preferences.entries.StandardEntry.badNameError"));
        }

        if (name.contains(PreferencesStorage.SEPARATOR)) {
            var path = new LinkedList<String>();

            Collections.addAll(path, name.split(PreferencesStorage.SEPARATOR));
            name = path.removeLast();

            try {
                for (String pathElement : path) {
                    storage = storage.child(pathElement);
                }
            } catch (Exception thrown) {
                throw new PreferencesException(
                    bundle.getString("com.github.windchopper.common.preferences.entries.StandardEntry.nameTraverseError"),
                    thrown);
            }
        }

        this.storage = storage;
        this.name = name;
        this.type = type;
    }

    @Override public PreferencesEntryValueHolder<T> load() throws PreferencesException {
        return type.load(storage, name);
    }

    @Override public void save(T value) throws PreferencesException {
        type.save(storage, name, value);
    }

}
