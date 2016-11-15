package name.wind.common.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Pipeliner<T> implements ReinforcedSupplier<T> {

    private final T value;

    private Pipeliner(T value) {
        this.value = value;
    }

    private Pipeliner(Supplier<T> supplier) {
        this.value = supplier.get();
    }

    public static <V> Pipeliner<V> of(V value) {
        return new Pipeliner<>(() -> value);
    }

    public static <V> Pipeliner<V> of(Supplier<V> supplier) {
        return new Pipeliner<>(supplier);
    }

    @Override public <V> ReinforcedSupplier<T> set(Function<T, Consumer<V>> consumerFunction, V value) {
        consumerFunction.apply(this.value).accept(value);
        return this;
    }

    @Override public <V> ReinforcedSupplier<T> add(Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        supplierFunction.apply(value).get().addAll(values);
        return this;
    }

    @Override public ReinforcedSupplier<T> accept(Consumer<T> consumer) {
        consumer.accept(value);
        return this;
    }

    @Override public T get() {
        return value;
    }

}
