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

public class Builder<T> implements ReinforcedSupplier<T> {

    private final Supplier<T> supplier;
    private final Queue<Consumer<T>> consumers = new LinkedList<>();

    private T built;

    private Builder(@Nonnull Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <V> Builder<V> of(@Nonnull Supplier<V> supplier) {
        return new Builder<>(supplier);
    }

    @Override public <V> Builder<T> set(@Nonnull Function<T, Consumer<V>> consumerFunction, V value) {
        consumers.add(target -> consumerFunction.apply(target).accept(value));
        return this;
    }

    @Override public <V> Builder<T> add(@Nonnull Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values) {
        consumers.add(target -> supplierFunction.apply(target).get().addAll(values));
        return this;
    }

    @Override public Builder<T> accept(@Nonnull Consumer<T> consumer) {
        consumers.add(consumer);
        return this;
    }

    @Override public <V> Builder<T> accept(@Nonnull BiConsumer<T, V> consumer, V value) {
        consumers.add(target -> consumer.accept(target, value));
        return this;
    }

    @Override public <V> Builder<V> map(@Nonnull Function<T, V> mapper) {
        return new Builder<>(() -> mapper.apply(get()));
    }

    @Override public T get() {
        return built = Optional.ofNullable(built).orElseGet(Pipeliner.of(supplier).accept(target -> consumers.forEach(consumer -> consumer.accept(target))));
    }

}
