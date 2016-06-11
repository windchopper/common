package name.wind.common.preferences;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectCollectionPreferencesEntry<T, C extends Collection<T>> extends StructuredPreferencesEntry<C> {

    public ObjectCollectionPreferencesEntry(Class<?> invoker, String name, Supplier<C> collectionSupplier,
                                            Function<StructuredValue, T> transformer, Function<T, StructuredValue> reverseTransformer) {
        super(
            invoker,
            name,
            structuredValue -> {
                C collection = collectionSupplier.get();
                structuredValue.children().forEach(child -> collection.add(transformer.apply(child)));
                return collection;
            },
            collection -> {
                StructuredValue structuredValue = new StructuredValue(name);
                collection.forEach(value -> structuredValue.children().add(reverseTransformer.apply(value)));
                return structuredValue;
            });
    }

}
