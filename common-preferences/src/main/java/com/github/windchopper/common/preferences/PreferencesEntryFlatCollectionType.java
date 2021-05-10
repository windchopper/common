package com.github.windchopper.common.preferences;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class PreferencesEntryFlatCollectionType<T, C extends Collection<T>> extends PreferencesEntryFlatContainerType<T, C> {

    private final Supplier<C> collectionFactory;

    public PreferencesEntryFlatCollectionType(Supplier<C> collectionFactory, PreferencesEntryFlatType<T> elementType) {
        super(elementType);
        this.collectionFactory = collectionFactory;
    }

    @Override protected C decode(Map<String, T> storageValue) {
        var collection = collectionFactory.get();

        storageValue.entrySet().stream()
            .sorted(Entry.comparingByKey())
            .map(Entry::getValue)
            .forEach(collection::add);

        return collection;
    }

    @Override protected Map<String, T> encode(C value) {
        var storageValue = new HashMap<String, T>();
        var index = 0;

        for (var element : value) {
            storageValue.put(Objects.toString(++index), element);
        }

        return storageValue;
    }

}
