package com.github.windchopper.common.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Factory<T> implements StatefulReinforcedSupplier<T> {

    private final Supplier<T> supplier;
    private final Queue<Consumer<T>> consumerQueue;

    private Factory(@Nonnull Supplier<T> supplier) {
        this(supplier, new LinkedList<>());
    }

    private Factory(@Nonnull Supplier<T> supplier, Queue<Consumer<T>> consumerQueue) {
        this.supplier = supplier;
        this.consumerQueue = consumerQueue;
    }

    public static <V> Factory<V> of(@Nonnull Supplier<V> supplier) {
        return new Factory<>(supplier);
    }

    @Override public Factory<T> copy() {
        return new Factory<>(supplier, consumerQueue);
    }

    @Override public <V> Factory<T> set(@Nonnull Function<T, Consumer<V>> consumerFunction, V value) {
        consumerQueue.add(target -> consumerFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Factory<T> add(@Nonnull Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        consumerQueue.add(target -> supplierFunction.apply(target).get().addAll(values));
        return this;
    }

    @Override public Factory<T> accept(@Nonnull Consumer<T> consumer) {
        consumerQueue.add(consumer);
        return this;
    }

    @Override public <V> Factory<T> accept(@Nonnull BiConsumer<T, V> consumer, V value) {
        consumerQueue.add(target -> consumer.accept(target, value));
        return this;
    }

    @Override public <V> Factory<V> map(@Nonnull Function<T, V> mapper) {
        return new Factory<>(() -> mapper.apply(get()));
    }

    @Override public T get() {
        return Pipeliner.of(supplier).accept(target -> consumerQueue.forEach(consumer -> consumer.accept(target))).get();
    }

}
