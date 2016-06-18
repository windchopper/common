package name.wind.common.preferences;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class StringCollectionPreferencesEntry<T, C extends Collection<T>> extends StructuredPreferencesEntry<C> {

    public StringCollectionPreferencesEntry(Class<?> invoker, String name, Supplier<C> collectionSupplier,
                                            Function<String, T> transformer, Function<T, String> reverseTransformer) {
        super(
            invoker,
            name,
            structuredValue -> {
                C collection = collectionSupplier.get();
                structuredValue.values().forEach(value -> collection.add(transformer.apply(value)));
                return collection;
            },
            collection -> {
                StructuredValue structuredValue = new StructuredValue(name);
                int idx = 0;
                for (T value : collection) structuredValue.put(Integer.toString(++idx), reverseTransformer.apply(value));
                return structuredValue;
            });
    }

}