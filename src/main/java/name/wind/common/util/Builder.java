package name.wind.common.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Builder<T> {

    <V> Builder<T> set(Function<T, Consumer<V>> consumerFunction, V value);
    <V> Builder<T> transform(Function<T, Consumer<V>> consumerFunction, Function<T, V> transformer);
    <V> Builder<T> add(Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values);
    Builder<T> accept(Consumer<T> consumer);
    T build();

}
