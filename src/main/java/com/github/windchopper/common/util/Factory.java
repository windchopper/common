package com.github.windchopper.common.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @Override public <V> Factory<T> set(Function<T, Consumer<V>> consumerFunction, V value) {
        consumerQueue.add(target -> consumerFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Factory<T> add(Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        consumerQueue.add(target -> supplierFunction.apply(target).get().addAll(values));
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
        return Pipeliner.of(supplier).accept(target -> consumerQueue.forEach(consumer -> consumer.accept(target))).get();
    }

}
