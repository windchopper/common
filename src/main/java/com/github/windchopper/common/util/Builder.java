package com.github.windchopper.common.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Builder<T> implements StatefulReinforcedSupplier<T> {

    private final Supplier<T> supplier;
    private final Queue<Consumer<T>> consumerQueue;

    private T built;

    private Builder(@Nonnull Supplier<T> supplier) {
        this(supplier, new LinkedList<>());
    }

    private Builder(@Nonnull Supplier<T> supplier, @Nonnull Queue<Consumer<T>> consumerQueue) {
        this.supplier = supplier;
        this.consumerQueue = consumerQueue;
    }

    public static <V> Builder<V> of(@Nonnull Supplier<V> supplier) {
        return new Builder<>(supplier);
    }

    @Override public Builder<T> copy() {
        return new Builder<>(supplier, consumerQueue);
    }

    @Override public <V> Builder<T> set(@Nonnull Function<T, Consumer<V>> consumerFunction, V value) {
        consumerQueue.add(target -> consumerFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Builder<T> add(@Nonnull Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        consumerQueue.add(target -> supplierFunction.apply(target).get().addAll(values));
        return this;
    }

    @Override public Builder<T> accept(@Nonnull Consumer<T> consumer) {
        consumerQueue.add(consumer);
        return this;
    }

    @Override public <V> Builder<T> accept(@Nonnull BiConsumer<T, V> consumer, V value) {
        consumerQueue.add(target -> consumer.accept(target, value));
        return this;
    }

    @Override public <V> Builder<V> map(@Nonnull Function<T, V> mapper) {
        return new Builder<>(() -> mapper.apply(get()));
    }

    @Override public T get() {
        return built = Optional.ofNullable(built).orElseGet(Pipeliner.of(supplier).accept(target -> consumerQueue.forEach(consumer -> consumer.accept(target))));
    }

}
