package com.github.windchopper.common.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Builder<T> implements ReinforcedSupplier<T> {

    private final Supplier<T> supplier;
    private final Queue<Consumer<T>> consumerQueue;

    private T built;

    private Builder(Supplier<T> supplier) {
        this(supplier, new LinkedList<>());
    }

    private Builder(Supplier<T> supplier, Queue<Consumer<T>> consumerQueue) {
        this.supplier = supplier;
        this.consumerQueue = consumerQueue;
    }

    public static <V> Builder<V> of(Supplier<V> supplier) {
        return new Builder<>(supplier);
    }

    @Override public <V> Builder<T> set(Function<T, Consumer<V>> setterFunction, V value) {
        consumerQueue.add(target -> setterFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Builder<T> add(Function<T, Supplier<Collection<V>>> collectionFunction, Collection<V> values) {
        consumerQueue.add(target -> collectionFunction.apply(target).get().addAll(values));
        return this;
    }

    @Override public Builder<T> accept(Consumer<T> consumer) {
        consumerQueue.add(consumer);
        return this;
    }

    @Override public <V> Builder<T> accept(BiConsumer<T, V> consumer, V value) {
        consumerQueue.add(target -> consumer.accept(target, value));
        return this;
    }

    @Override public <V> Builder<V> map(Function<T, V> mapper) {
        return new Builder<>(() -> mapper.apply(get()));
    }

    @Override public T get() {
        return built = Optional.ofNullable(built)
            .orElseGet(() -> Pipeliner.of(supplier)
                .accept(target -> consumerQueue.forEach(consumer -> consumer.accept(target)))
                .get());
    }

}
