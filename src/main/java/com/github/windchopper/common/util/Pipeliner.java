package com.github.windchopper.common.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Pipeliner<T> implements ReinforcedSupplier<T> {

    private final T value;

    private Pipeliner(Supplier<T> supplier) {
        value = Objects.requireNonNull(
            supplier.get());
    }

    public static <V> Pipeliner<V> of(@Nonnull V value) {
        return new Pipeliner<>(() -> value);
    }

    public static <V> Pipeliner<V> of(@Nonnull Supplier<V> supplier) {
        return new Pipeliner<>(supplier);
    }

    @Override public <V> Pipeliner<T> set(@Nonnull Function<T, Consumer<V>> consumerFunction, V value) {
        consumerFunction.apply(this.value).accept(value);
        return this;
    }

    @Override public <V> Pipeliner<T> add(@Nonnull Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        supplierFunction.apply(value).get().addAll(values);
        return this;
    }

    @Override public Pipeliner<T> accept(@Nonnull Consumer<T> consumer) {
        consumer.accept(value);
        return this;
    }

    public <O> Pipeliner<O> map(@Nonnull Function<? super T, ? extends O> mapper) {
        return new Pipeliner<>(() -> mapper.apply(value));
    }

    @Override public T get() {
        return value;
    }

}
