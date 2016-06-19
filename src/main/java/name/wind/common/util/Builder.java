package name.wind.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Builder<T> implements Supplier<T> {

    public abstract <V> Builder<T> set(Function<T, Consumer<V>> valueConsumerFunction, V value);
    public abstract <V> Builder<T> transform(Function<T, Consumer<V>> valueConsumerFunction, Function<T, V> valueFunction);
    public abstract <V> Builder<T> add(Function<T, Supplier<Collection<V>>> valueCollectionSupplierFunction, Collection<V> valueCollection);
    public abstract Builder<T> accept(Consumer<T> consumer);

    /*
     *
     */

    public static <T> Builder<T> direct(Supplier<T> supplier) {
        return new DirectBuilder<>(supplier);
    }

    public static <K, V> Builder<Map<K, V>> directMapBuilder(Supplier<Map<K, V>> supplier) {
        return new DirectBuilder<>(supplier);
    }

    public static <T> Builder<T> lazy(Supplier<T> supplier) {
        return new LazyBuilder<>(supplier);
    }

    /*
     *
     */

    private static class DirectBuilder<T> extends Builder<T> {

        private final T target;

        DirectBuilder(Supplier<T> supplier) {
            target = supplier.get();
        }

        @Override public <V> DirectBuilder<T> set(Function<T, Consumer<V>> valueConsumerFunction, V value) {
            valueConsumerFunction.apply(target).accept(value);
            return this;
        }

        @Override public <V> Builder<T> transform(Function<T, Consumer<V>> valueConsumerFunction, Function<T, V> valueFunction) {
            valueConsumerFunction.apply(target).accept(valueFunction.apply(target));
            return this;
        }

        @Override public <V> DirectBuilder<T> add(Function<T, Supplier<Collection<V>>> valueCollectionSupplierFunction, Collection<V> valueCollection) {
            valueCollectionSupplierFunction.apply(target).get().addAll(valueCollection);
            return this;
        }

        @Override public DirectBuilder<T> accept(Consumer<T> consumer) {
            consumer.accept(target);
            return this;
        }

        @Override public T get() {
            return target;
        }

    }

    private static class LazyBuilder<T> extends Builder<T> {

        private final Supplier<T> supplier;
        private final List<Consumer<T>> consumers = new ArrayList<>();

        private T value;

        LazyBuilder(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override public <V> LazyBuilder<T> set(Function<T, Consumer<V>> valueConsumerFunction, V value) {
            consumers.add(target -> valueConsumerFunction.apply(target).accept(value));
            return this;
        }

        @Override public <V> Builder<T> transform(Function<T, Consumer<V>> valueConsumerFunction, Function<T, V> valueFunction) {
            consumers.add(target -> valueConsumerFunction.apply(target).accept(valueFunction.apply(target)));
            return this;
        }

        @Override public <V> LazyBuilder<T> add(Function<T, Supplier<Collection<V>>> valueCollectionSupplierFunction, Collection<V> valueCollection) {
            consumers.add(target -> valueCollectionSupplierFunction.apply(target).get().addAll(valueCollection));
            return this;
        }

        @Override public LazyBuilder<T> accept(Consumer<T> consumer) {
            consumers.add(consumer);
            return this;
        }

        @Override public T get() {
            return Value
                .of(
                    Value
                        .of(value)
                        .orElseGet(supplier))
                .ifPresent(
                    value -> consumers.forEach(
                        consumer -> consumer.accept(value)))
                .orElseThrow(
                    NullPointerException::new);
        }

    }

}