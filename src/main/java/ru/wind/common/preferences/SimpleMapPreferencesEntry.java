package ru.wind.common.preferences;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleMapPreferencesEntry<T, M extends Map<String, T>> extends MapPreferencesEntry<String, T, M> {

    public SimpleMapPreferencesEntry(Class<?> invoker, String name, Supplier<M> mapSupplier, Function<String, T> transformer, Function<T, String> reverseTransformer) {
        super(
            invoker,
            name,
            structuredValue -> {
                M map = mapSupplier.get();
                structuredValue.entrySet().forEach(entry -> map.put(entry.getKey(), transformer.apply(entry.getValue())));
                return map;
            },
            map -> {
                StructuredValue structuredValue = new StructuredValue(name);
                map.entrySet().forEach(entry -> structuredValue.put(entry.getKey(), reverseTransformer.apply(entry.getValue())));
                return structuredValue;
            });
    }

}
