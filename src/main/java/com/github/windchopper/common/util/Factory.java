package com.github.windchopper.common.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Factory<T> implements ReinforcedSupplier<T> {

    private final Supplier<T> supplier;
    private final Queue<Consumer<T>> consumers = new LinkedList<>();

    private Factory(@Nonnull Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <V> Factory<V> of(@Nonnull Supplier<V> supplier) {
        return new Factory<>(supplier);
    }

    @Override public <V> Factory<T> set(@Nonnull Function<T, Consumer<V>> consumerFunction, V value) {
        consumers.add(target -> consumerFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Factory<T> add(@Nonnull Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        consumers.add(target -> supplierFunction.apply(target).get().addAll(values));
        return this;
    }

    @Override public Factory<T> accept(@Nonnull Consumer<T> consumer) {
        consumers.add(consumer);
        return this;
    }

    @Override public <V> Factory<T> accept(@Nonnull BiConsumer<T, V> consumer, V value) {
        consumers.add(target -> consumer.accept(target, value));
        return this;
    }

    @Override public T get() {
        return Pipeliner.of(supplier).accept(target -> consumers.forEach(consumer -> consumer.accept(target))).get();
    }

}
