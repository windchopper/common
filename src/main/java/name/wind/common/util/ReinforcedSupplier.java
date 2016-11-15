package name.wind.common.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ReinforcedSupplier<T> extends Supplier<T> {

    <V> ReinforcedSupplier<T> set(Function<T, Consumer<V>> consumerFunction, V value);
    <V> ReinforcedSupplier<T> add(Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values);
    ReinforcedSupplier<T> accept(Consumer<T> consumer);

}
