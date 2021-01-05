package com.github.windchopper.common.preferences;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class PreferencesEntryFlatCollectionType<T, C extends Collection<T>> extends PreferencesEntryType<C, Map<String, T>> {

    private final Supplier<C> collectionFactory;
    private final PreferencesEntryFlatType<T> elementType;

    public PreferencesEntryFlatCollectionType(Supplier<C> collectionFactory, PreferencesEntryFlatType<T> elementType) {
        this.collectionFactory = collectionFactory;
        this.elementType = elementType;
    }

    @Override protected C decode(Map<String, T> storageValue) throws Exception {
        var collection = collectionFactory.get();

        storageValue.entrySet().stream()
            .sorted(Entry.comparingByKey())
            .map(Entry::getValue)
            .forEach(collection::add);

        return collection;
    }

    @Override protected Map<String, T> loadValue(PreferencesStorage storage, String name) throws Exception {
        var storageValue = new HashMap<String, T>();

        for (String elementName : storage.valueNames()) {
            storageValue.put(elementName, elementType.decode(elementType.loadValue(storage, elementName)));
        }

        return storageValue;
    }

    @Override protected Map<String, T> encode(C value) throws Exception {
        var storageValue = new HashMap<String, T>();
        var index = 0;

        for (T element : value) {
            storageValue.put(Objects.toString(++index), element);
        }

        return storageValue;
    }

    @Override protected void saveValue(PreferencesStorage storage, String name, Map<String, T> storageValue) throws Exception {
        for (Entry<String, T> entry : storageValue.entrySet()) {
            elementType.saveValue(storage, entry.getKey(), elementType.encode(entry.getValue()));
        }
    }

}
