package com.github.windchopper.common.preferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class PreferencesEntryFlatMapType<T, M extends Map<String, T>> extends PreferencesEntryType<M, Map<String, T>> {

    private final Supplier<M> mapFactory;
    private final PreferencesEntryFlatType<T> valueType;

    public PreferencesEntryFlatMapType(Supplier<M> mapFactory, PreferencesEntryFlatType<T> valueType) {
        this.mapFactory = mapFactory;
        this.valueType = valueType;
    }

    @Override protected M decode(Map<String, T> storageValue) throws Exception {
        var map = mapFactory.get();

        map.putAll(storageValue);

        return map;
    }

    @Override protected Map<String, T> loadValue(PreferencesStorage storage, String name) throws Exception {
        var storageValue = new HashMap<String, T>();

        for (String elementName : storage.valueNames()) {
            storageValue.put(elementName, valueType.decode(valueType.loadValue(storage, elementName)));
        }

        return storageValue;
    }

    @Override protected Map<String, T> encode(M value) throws Exception {
        return value;
    }

    @Override protected void saveValue(PreferencesStorage storage, String name, Map<String, T> storageValue) throws Exception {
        for (Entry<String, T> entry : storageValue.entrySet()) {
            valueType.saveValue(storage, entry.getKey(), valueType.encode(entry.getValue()));
        }
    }

}
