package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.BufferedReference;

import java.time.Duration;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class PreferencesEntry<T> implements Supplier<T>, Consumer<T> {

    private static final String BUNDLE_KEY__NULL_PARAMETER = "com.github.windchopper.common.preferences.nullParameter";
    private static final String BUNDLE_KEY__LOAD_FAIL = "com.github.windchopper.common.preferences.PreferencesEntry.fail.load";
    private static final String BUNDLE_KEY__SAVE_FAIL = "com.github.windchopper.common.preferences.PreferencesEntry.fail.save";

    private static final Logger logger = Logger.getLogger("com.github.windchopper.common.preferences");
    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.preferences.i18n.messages");

    private final PreferencesStorage storage;
    private final String name;
    private final PreferencesEntryType<T> type;
    private final BufferedReference<T> storedValueReference;

    public PreferencesEntry(PreferencesStorage storage, String name, PreferencesEntryType<T> type, Duration bufferLifetime) {
        this.storage = requireNonNull(storage, String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "storage"));
        this.name = requireNonNull(name, String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "name"));
        this.type = requireNonNull(type, String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "type"));
        storedValueReference = new BufferedReference<>(
            requireNonNull(bufferLifetime, String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "bufferLifetime")),
            this::load);
    }

    private T load() {
        try {
            return type.load(storage, name);
        } catch (Exception thrown) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format(bundle.getString(BUNDLE_KEY__LOAD_FAIL), name), thrown);
            }
        }

        return null;
    }

    @Override public T get() {
        return storedValueReference.get();
    }

    @Override public void accept(T value) {
        try {
            type.save(storage, name, value);
            storedValueReference.invalidate();
        } catch (Exception thrown) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, String.format(bundle.getString(BUNDLE_KEY__SAVE_FAIL), name), thrown);
            }
        }
    }

}
