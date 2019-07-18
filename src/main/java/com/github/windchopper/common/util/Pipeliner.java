package com.github.windchopper.common.util;

import com.github.windchopper.common.util.stream.FailableFunction;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Pipeliner<T> implements ReinforcedSupplier<T> {

    private final T target;

    private Pipeliner(Supplier<T> supplier) {
        this(Objects.requireNonNull(supplier).get());
    }

    private Pipeliner(T value) {
        target = Objects.requireNonNull(value);
    }

    public static <V> Pipeliner<V> of(V value) {
        return new Pipeliner<>(value);
    }

    public static <V> Pipeliner<V> of(Supplier<V> supplier) {
        return new Pipeliner<>(supplier);
    }

    @Override public <V> Pipeliner<T> set(Function<T, Consumer<V>> setterFunction, V value) {
        setterFunction.apply(target).accept(value);
        return this;
    }

    @Override public <V> Pipeliner<T> add(Function<T, Supplier<Collection<V>>> collectionFunction, Collection<V> values) {
        collectionFunction.apply(target).get().addAll(values);
        return this;
    }

    @Override public Pipeliner<T> accept(Consumer<T> consumer) {
        consumer.accept(target);
        return this;
    }

    @Override public <V> Pipeliner<T> accept(BiConsumer<T, V> consumer, V value) {
        consumer.accept(target, value);
        return this;
    }

    @Override public <V> Pipeliner<V> map(Function<T, V> mapper) {
        return new Pipeliner<>(() -> mapper.apply(target));
    }

    public <V, E extends Throwable> Pipeliner<V> mapFailable(FailableFunction<T, V, E> mapper) throws E {
        return new Pipeliner<>(mapper.apply(target));
    }

    @Override public T get() {
        return target;
    }

}
