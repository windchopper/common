package ru.wind.common.preferences;

import java.util.Collection;
import java.util.function.Function;

public class CollectionPreferencesEntry<T, C extends Collection<T>> extends StructuredPreferencesEntry<C> {

    public CollectionPreferencesEntry(Class<?> invoker, String name, Function<StructuredValue, C> transformer, Function<C, StructuredValue> reverseTransformer) {
        super(invoker, name, transformer, reverseTransformer);
    }

}
