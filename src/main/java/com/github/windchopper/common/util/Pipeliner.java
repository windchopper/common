package com.github.windchopper.common.util;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Pipeliner<T> implements ReinforcedSupplier<T> {

    private final T target;

    private Pipeliner(Supplier<T> supplier) {
        target = Objects.requireNonNull(supplier.get());
    }

    public static <V> Pipeliner<V> of(V value) {
        return new Pipeliner<V>(() -> value);
    }

    public static <V> Pipeliner<V> of(Supplier<V> supplier) {
        return new Pipeliner<>(supplier);
    }

    @Override public <V> Pipeliner<T> set(Function<T, Consumer<V>> consumerFunction, V value) {
        consumerFunction.apply(this.target).accept(value);
        return this;
    }

    @Override public <V> Pipeliner<T> add(Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        supplierFunction.apply(target).get().addAll(values);
        return this;
    }

    @Override public Pipeliner<T> accept(Consumer<T> consumer) {
        consumer.accept(target);
        return this;
    }

    @Override public <V> Pipeliner<T> accept(BiConsumer<T, V> consumer, V argument) {
        consumer.accept(target, argument);
        return this;
    }

    @Override public <V> Pipeliner<V> map(Function<T, V> mapper) {
        return new Pipeliner<>(() -> mapper.apply(target));
    }

    @Override public T get() {
        return target;
    }

}
