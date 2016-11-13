package name.wind.common.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LazyBuilder<T> implements Builder<T> {

    private final Supplier<T> supplier;
    private final Queue<Consumer<T>> consumers = new LinkedList<>();

    private T built;

    public LazyBuilder(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override public <V> Builder<T> set(Function<T, Consumer<V>> consumerFunction, V value) {
        consumers.add(target -> consumerFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Builder<T> transform(Function<T, Consumer<V>> consumerFunction, Function<T, V> transformer) {
        consumers.add(target -> consumerFunction.apply(target).accept(transformer.apply(target)));
        return this;
    }

    @Override public <V> Builder<T> add(Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        consumers.add(target -> supplierFunction.apply(target).get().addAll(values));
        return this;
    }

    @Override public Builder<T> accept(Consumer<T> consumer) {
        consumers.add(consumer);
        return this;
    }

    @Override public T build() {
        return built = Optional.of(built).orElseGet(() -> {
            T suppliedValue = supplier.get();
            consumers.forEach(consumer -> consumer.accept(suppliedValue));
            return suppliedValue;
        });
    }

}
