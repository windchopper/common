package name.wind.common.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class InstantBuilder<T> implements Builder<T> {

    private final T built;

    public InstantBuilder(Supplier<T> supplier) {
        built = supplier.get();
    }

    @Override public <V> Builder<T> set(Function<T, Consumer<V>> consumerFunction, V value) {
        consumerFunction.apply(built).accept(value);
        return this;
    }

    @Override public <V> Builder<T> transform(Function<T, Consumer<V>> consumerFunction, Function<T, V> transformer) {
        consumerFunction.apply(built).accept(transformer.apply(built));
        return this;
    }

    @Override public <V> Builder<T> add(Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        supplierFunction.apply(built).get().addAll(values);
        return this;
    }

    @Override public Builder<T> accept(Consumer<T> consumer) {
        consumer.accept(built);
        return this;
    }

    @Override public T build() {
        return built;
    }

}
