package ru.wind.common.preferences;

import java.util.Map;
import java.util.function.Function;

public class MapPreferencesEntry<K, V, M extends Map<K, V>> extends StructuredPreferencesEntry<M> {

    public MapPreferencesEntry(Class<?> invoker, String name, Function<StructuredValue, M> transformer, Function<M, StructuredValue> reverseTransformer) {
        super(invoker, name, transformer, reverseTransformer);
    }

}
