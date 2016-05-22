package ru.wind.common.util;

import ru.wind.common.util.function.Consumer;
import ru.wind.common.util.function.Function;
import ru.wind.common.util.function.Performer;
import ru.wind.common.util.function.Supplier;

import java.util.ArrayList;
import java.util.Collection;

public class Builder<T, E extends Exception> {

    private Supplier<T, E> targetSupplier;
    private Collection<Performer<E>> buildPerformers;

    public Builder(Supplier<T, E> supplier) {
        this.targetSupplier = supplier;
    }

    public Builder<T, E> deferred() {
        buildPerformers = new ArrayList<>();
        return this;
    }

    public <V> Builder<T, E> set(Function<T, Consumer<V, E>, E> consumerFunction, Supplier<V, E> valueSupplier) throws E {
        if (buildPerformers != null) {
            buildPerformers.add(() -> consumerFunction.apply(targetSupplier.supply()).accept(valueSupplier.supply()));
        } else {
            consumerFunction.apply(targetSupplier.supply()).accept(valueSupplier.supply());
        }

        return this;
    }

    public <V> Builder<T, E> add(Function<T, Supplier<Collection<V>, E>, E> collectionSupplier, Supplier<Collection<V>, E> valueCollectionSupplier) throws E {
        if (buildPerformers != null) {
            buildPerformers.add(() -> collectionSupplier.apply(targetSupplier.supply()).supply().addAll(valueCollectionSupplier.supply()));
        } else {
            collectionSupplier.apply(targetSupplier.supply()).supply().addAll(valueCollectionSupplier.supply());
        }

        return this;
    }

    public T build() throws E {
        T object = targetSupplier.supply();

        if (buildPerformers != null) {
            for (Performer<E> atomic : buildPerformers) {
                atomic.perform();
            }
        }

        return object;
    }

}
