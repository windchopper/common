package com.github.windchopper.common.preferences;

import java.util.Map;
import java.util.function.Supplier;

public class PreferencesEntryFlatMapType<T, M extends Map<String, T>> extends PreferencesEntryFlatContainerType<T, M> {

    private final Supplier<M> mapFactory;

    public PreferencesEntryFlatMapType(Supplier<M> mapFactory, PreferencesEntryFlatType<T> valueType) {
        super(valueType);
        this.mapFactory = mapFactory;
    }

    @Override protected M decode(Map<String, T> storageValue) {
        var map = mapFactory.get();

        map.putAll(storageValue);

        return map;
    }

    @Override protected Map<String, T> encode(M value) {
        return value;
    }

}
