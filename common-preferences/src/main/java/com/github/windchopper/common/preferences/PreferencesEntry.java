package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.BufferedReference;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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
    private final BufferedReference<T, RuntimeException> storedValue;

    public PreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type, Duration bufferLifetime) {
        this(storage, name, type, null, bufferLifetime);
    }

    public PreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type, T defaultValue) {
        this(storage, name, type, defaultValue, ChronoUnit.FOREVER.getDuration());
    }

    public PreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type) {
        this(storage, name, type, null, ChronoUnit.FOREVER.getDuration());
    }

    public PreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type, T defaultValue, Duration bufferLifetime) {
        this.storage = requireNonNull(storage, "storage");
        this.name = requireNonNull(name, "name");
        this.type = requireNonNull(type, "type");

        storedValue = new BufferedReference<>(
            requireNonNull(bufferLifetime, "bufferLifetime"),
            () -> {
                T value = null;

                try {
                    value = type.load(storage, name);
                } catch (Exception thrown) {
                    logger.log(Level.SEVERE, String.format(bundle.getString("com.github.windchopper.common.preferences.PreferencesEntry.fail.load"), name), thrown);
                }

                if (value == null && defaultValue != null) {
                    save(value = defaultValue);
                }

                return value;
            });
    }

    public T load() {
        return storedValue.get();
    }

    public void save(T value) {
        try {
            type.save(storage, name, value);
            storedValue.invalidate();
        } catch (Exception thrown) {
            logger.log(Level.SEVERE, String.format(bundle.getString("com.github.windchopper.common.preferences.PreferencesEntry.fail.save"), name), thrown);
        }
    }

}