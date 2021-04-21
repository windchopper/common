package com.github.windchopper.common.util;

import java.util.*;
import java.util.function.*;

public class Factory<T> implements ReinforcedSupplier<T> {

    private final Supplier<T> supplier;
    private final Queue<Consumer<T>> consumerQueue;

    private Factory(Supplier<T> supplier) {
        this(supplier, new LinkedList<>());
    }

    private Factory(Supplier<T> supplier, Queue<Consumer<T>> consumerQueue) {
        this.supplier = supplier;
        this.consumerQueue = consumerQueue;
    }

    public static <V> Factory<V> of(Supplier<V> supplier) {
        return new Factory<>(supplier);
    }

    @Override public <V> Factory<T> set(Function<T, Consumer<V>> setterFunction, V value) {
        consumerQueue.add(target -> setterFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Factory<T> add(Function<T, Supplier<Collection<V>>> collectionFunction, Iterable<V> values) {
        consumerQueue.add(target -> values.forEach(collectionFunction.apply(target).get()::add));
        return this;
    }

    @Override public Factory<T> accept(Consumer<T> consumer) {
        consumerQueue.add(consumer);
        return this;
    }

    @Override public <V> Factory<T> accept(BiConsumer<T, V> consumer, V value) {
        consumerQueue.add(target -> consumer.accept(target, value));
        return this;
    }

    @Override public <V> Factory<V> map(Function<T, V> mapper) {
        return new Factory<>(() -> mapper.apply(get()));
    }

    @Override public T get() {
        return Pipeliner.of(supplier)
            .accept(target -> consumerQueue.forEach(consumer -> consumer.accept(target)))
            .get();
    }

}
