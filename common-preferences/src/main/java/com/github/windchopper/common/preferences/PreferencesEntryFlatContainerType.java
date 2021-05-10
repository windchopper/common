package com.github.windchopper.common.preferences;

import java.util.HashMap;
import java.util.Map;

import static java.util.function.Predicate.not;

public abstract class PreferencesEntryFlatContainerType<T, C> extends PreferencesEntryType<C, Map<String, T>> {

    private final PreferencesEntryFlatType<T> valueType;

    public PreferencesEntryFlatContainerType(PreferencesEntryFlatType<T> valueType) {
        this.valueType = valueType;
    }

    @Override protected Map<String, T> loadValue(PreferencesStorage storage, String name) throws Throwable {
        var storageValue = new HashMap<String, T>();
        Iterable<String> elementNames = storage.valueNames().stream()
            .filter(not("timestamp"::equals))::iterator;

        for (var elementName : elementNames) {
            storageValue.put(elementName, valueType.decode(valueType.loadValue(storage, elementName)));
        }

        return storageValue;
    }

    @Override protected void saveValue(PreferencesStorage storage, String name, Map<String, T> storageValue) throws Throwable {
        for (var entry : storageValue.entrySet()) {
            valueType.saveValue(storage, entry.getKey(), valueType.encode(entry.getValue()));
        }
    }

}
