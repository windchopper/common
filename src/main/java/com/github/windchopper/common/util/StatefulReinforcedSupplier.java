package com.github.windchopper.common.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface StatefulReinforcedSupplier<T> extends ReinforcedSupplier<T> {

    @Override <V> StatefulReinforcedSupplier<T> set(@Nonnull Function<T, Consumer<V>> consumerFunction, V value);
    @Override <V> StatefulReinforcedSupplier<T> add(@Nonnull Function<T, Supplier<Collection<V>>> supplierFunction, Collection<V> values);
    @Override StatefulReinforcedSupplier<T> accept(@Nonnull Consumer<T> consumer);
    @Override <V> StatefulReinforcedSupplier<T> accept(@Nonnull BiConsumer<T, V> consumer, V value);
    @Override <V> StatefulReinforcedSupplier<V> map(@Nonnull Function<T, V> mapper);

    StatefulReinforcedSupplier<T> copy();

}
