package ru.wind.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Builder<T> implements Supplier<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");

    private Supplier<T> targetSupplier;
    private Collection<Runnable> buildPerformers;

    public Builder(Supplier<T> targetSupplier) {
        this.targetSupplier = targetSupplier;
    }

    public Builder<T> lazy() {
        if (buildPerformers == null) {
            buildPerformers = new ArrayList<>();
        }

        return this;
    }

    public <V> Builder<T> set(Function<T, Consumer<V>> consumerFunction, Supplier<V> valueSupplier) {
        if (buildPerformers != null) {
            buildPerformers.add(() -> consumerFunction.apply(targetSupplier.get()).accept(valueSupplier.get()));
        } else {
            consumerFunction.apply(targetSupplier.get()).accept(valueSupplier.get());
        }

        return this;
    }

    public <V> Builder<T> add(Function<T, Supplier<Collection<V>>> collectionSupplier, Supplier<Collection<V>> valueCollectionSupplier) {
        if (buildPerformers != null) {
            buildPerformers.add(() -> collectionSupplier.apply(targetSupplier.get()).get().addAll(valueCollectionSupplier.get()));
        } else {
            collectionSupplier.apply(targetSupplier.get()).get().addAll(valueCollectionSupplier.get());
        }

        return this;
    }

    public Builder<T> accept(Consumer<T> targetConsumer) throws IllegalStateException {
        if (buildPerformers != null) {
            throw new IllegalStateException(bundle.getString("Not supported for lazy builder"));
        } else {
            targetConsumer.accept(targetSupplier.get());
        }

        return this;
    }

    @Override public T get() {
        T object = targetSupplier.get();

        if (buildPerformers != null) {
            buildPerformers.forEach(Runnable::run);
        }

        return object;
    }

}
