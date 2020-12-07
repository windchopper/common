package com.github.windchopper.common.preferences;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class PreferencesEntry<T> {

    private static final Logger logger = Logger.getLogger(PreferencesEntry.class.getName());
    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.preferences.i18n.messages");

    private final PreferencesStorage storage;
    private final String name;
    private final PreferencesEntryType<T> type;

    public PreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type) {
        this.storage = requireNonNull(storage, "storage");
        this.name = requireNonNull(name, "name");
        this.type = requireNonNull(type, "type");
    }

    public T load() throws PreferencesException {
        T value = null;

        try {
            value = type.load(storage, name);
        } catch (PreferencesException thrown) {
            throw thrown;
        } catch (Exception thrown) {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, String.format(bundle.getString("com.github.windchopper.common.preferences.PreferencesEntry.fail.load"),
                    thrown.getMessage()), thrown);
            }
        }

        return value;
    }

    public void save(T value) throws PreferencesException {
        try {
            type.save(storage, name, value);
        } catch (PreferencesException thrown) {
            throw thrown;
        } catch (Exception thrown) {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, String.format(bundle.getString("com.github.windchopper.common.preferences.PreferencesEntry.fail.save"),
                    thrown.getMessage()), thrown);
            }
        }
    }

}
