package com.github.windchopper.common.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ReinforcedSupplier<T> extends Supplier<T> {

    <V> ReinforcedSupplier<T> set(@Nonnull Function<T, Consumer<V>> consumerFunction, V value);
    <V> ReinforcedSupplier<T> add(@Nonnull Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values);
    ReinforcedSupplier<T> accept(@Nonnull Consumer<T> consumer);
    <V> ReinforcedSupplier<T> accept(@Nonnull BiConsumer<T, V> consumer, V value);

}
