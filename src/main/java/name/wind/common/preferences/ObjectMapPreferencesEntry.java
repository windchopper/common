package name.wind.common.preferences;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ObjectMapPreferencesEntry<V, M extends Map<String, V>> extends StructuredPreferencesEntry<M> {

    public ObjectMapPreferencesEntry(Class<?> invoker, String name, Supplier<M> mapSupplier,
                                     Function<StructuredValue, V> transformer, BiFunction<String, V, StructuredValue> reverseTransformer) {
        super(
            invoker,
            name,
            structuredValue -> {
                M map = mapSupplier.get();
                structuredValue.children().forEach(child -> map.put(child.name(), transformer.apply(child)));
                return map;
            },
            map -> {
                StructuredValue structuredValue = new StructuredValue(name);
                map.entrySet().forEach(entry -> reverseTransformer.apply(entry.getKey(), entry.getValue()));
                return structuredValue;
            });
    }

}
