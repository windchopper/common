package com.github.windchopper.common.util;

import java.util.Collection;
import java.util.function.*;

public interface ReinforcedSupplier<T> extends Supplier<T> {

    <V> ReinforcedSupplier<T> set(Function<T, Consumer<V>> setterFunction, V value);
    <V> ReinforcedSupplier<T> add(Function<T, Supplier<Collection<V>>> collectionFunction, Iterable<V> values);
    ReinforcedSupplier<T> accept(Consumer<T> consumer);
    <V> ReinforcedSupplier<T> accept(BiConsumer<T, V> consumer, V value);
    <V> ReinforcedSupplier<V> map(Function<T, V> mapper);

}
