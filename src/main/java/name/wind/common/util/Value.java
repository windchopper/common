package name.wind.common.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Value<T> implements Supplier<T> {

    private final T value;

    public Value(T value) {
        this.value = value;
    }

    public static <T> Value<T> of(T value) {
        return new Value<>(value);
    }

    public static <T> Value<T> of(Supplier<T> valueSupplier) {
        return new Value<>(valueSupplier.get());
    }

    @Override public T get() {
        return value;
    }

    public Value<T> with(Consumer<? super T> consumer) {
        consumer.accept(value);
        return this;
    }

}
