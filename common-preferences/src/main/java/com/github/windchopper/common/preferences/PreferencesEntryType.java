package com.github.windchopper.common.preferences;

import java.time.Instant;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.util.function.Predicate.not;

public abstract class PreferencesEntryType<ValueType, StorageValueType> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.preferences.i18n.messages");

    protected abstract ValueType decode(StorageValueType storageValue) throws Exception;

    protected abstract StorageValueType loadInternal(PreferencesStorage storage) throws Exception;

    protected PreferencesEntryValueHolder<ValueType> loadInternal(PreferencesStorage storage, String name) throws Exception {
        var holder = new PreferencesEntryValueHolder<ValueType>();
        var node = storage.child(name);

        holder.setValue(decode(loadInternal(node)));
        holder.setTimestamp(Optional.ofNullable(node.value("timestamp"))
            .filter(not(String::isBlank))
            .map(Instant::parse)
            .or(storage::timestamp)
            .orElse(Instant.MIN));

        return holder;
    }

    public PreferencesEntryValueHolder<ValueType> load(PreferencesStorage storage, String name) throws PreferencesException {
        try {
            return loadInternal(storage, name);
        } catch (PreferencesException thrown) {
            throw thrown;
        } catch (Exception thrown) {
            throw new PreferencesException(
                bundle.getString("com.github.windchopper.common.preferences.PreferencesEntryType.loadError"),
                thrown);
        }
    }

    protected abstract StorageValueType encode(ValueType value) throws Exception;

    protected abstract void saveInternal(PreferencesStorage storage, StorageValueType storageValue) throws Exception;

    protected void saveInternal(PreferencesStorage storage, String name, ValueType value) throws Exception {
        var node = storage.child(name);

        for (String key : node.childNames()) {
            node.dropChild(key);
        }

        for (String key : node.valueNames()) {
            node.dropValue(key);
        }

        node.saveValue("timestamp", Instant.now().toString());
        saveInternal(node, encode(value));
    }

    public void save(PreferencesStorage storage, String name, ValueType value) throws PreferencesException {
        try {
            saveInternal(storage, name, value);
        } catch (PreferencesException thrown) {
            throw thrown;
        } catch (Exception thrown) {
            throw new PreferencesException(
                bundle.getString("com.github.windchopper.common.preferences.PreferencesEntryType.saveError"),
                thrown);
        }
    }

}
