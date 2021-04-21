package com.github.windchopper.common.preferences;

import com.github.windchopper.common.util.stream.Fallible;

import java.time.Instant;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.function.Predicate.not;

public abstract class PreferencesEntryType<ValueType, StorageValueType> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.preferences.i18n.messages");

    protected abstract ValueType decode(StorageValueType storageValue) throws Throwable;

    protected abstract StorageValueType loadValue(PreferencesStorage storage, String name) throws Throwable;

    protected PreferencesEntryValueHolder<ValueType> loadInternal(PreferencesStorage storage, String name) throws Throwable {
        var holder = new PreferencesEntryValueHolder<ValueType>();
        var node = storage.child(name);

        holder.setValue(decode(loadValue(node, "value")));
        holder.setTimestamp(Optional.ofNullable(node.value("timestamp"))
            .filter(not(String::isBlank))
            .map(Instant::parse)
            .or(storage::timestamp)
            .orElse(null));

        return holder;
    }

    protected PreferencesException convert(String bundleKey, Throwable throwable) {
        if (throwable instanceof PreferencesException preferencesException) {
            return preferencesException;
        } else {
            throw new PreferencesException(
                bundle.getString(bundleKey),
                throwable);
        }
    }

    public PreferencesEntryValueHolder<ValueType> load(PreferencesStorage storage, String name) throws PreferencesException {
        return Fallible.rethrow(
            (thrown) -> convert("com.github.windchopper.common.preferences.PreferencesEntryType.loadError", thrown),
            () -> loadInternal(storage, name));
    }

    protected abstract StorageValueType encode(ValueType value) throws Throwable;

    protected abstract void saveValue(PreferencesStorage storage, String name, StorageValueType storageValue) throws Throwable;

    protected void saveInternal(PreferencesStorage storage, String name, ValueType value) throws Throwable {
        var node = storage.child(name);

        for (String key : node.childNames()) {
            node.dropChild(key);
        }

        for (String key : node.valueNames()) {
            node.dropValue(key);
        }

        node.saveValue("timestamp", Instant.now().toString());
        saveValue(node, "value", encode(value));
    }

    public void save(PreferencesStorage storage, String name, ValueType value) throws PreferencesException {
        Fallible.rethrow(
            (thrown) -> convert("com.github.windchopper.common.preferences.PreferencesEntryType.saveError", thrown),
            () -> saveInternal(storage, name, value));
    }

}
