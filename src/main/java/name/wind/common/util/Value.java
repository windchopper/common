package name.wind.common.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Value<T> implements Supplier<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("name.wind.common.i18n.messages");
    private static final Value<?> empty = new Value<>(null);

    private final T value;

    private Value(T value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked") public static <T> Value<T> empty() {
        return (Value<T>) empty;
    }

    public static <T> Value<T> of(T value) {
        return new Value<>(value);
    }

    public static <T> Value<T> of(Supplier<T> valueSupplier) {
        return new Value<>(valueSupplier.get());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") public static <T> Value<T> of(java.util.Optional<T> optional) {
        return new Value<>(optional.orElse(null));
    }

    public boolean present() {
        return value != null;
    }

    public boolean notPresent() {
        return value == null;
    }

    @Override public T get() {
        if (value == null) {
            throw new NoSuchElementException(bundle.getString("name.wind.common.util.Value.nullValue"));
        }

        return value;
    }

    public <U> Value<U> map(Function<? super T, ? extends U> mapper) {
        if (value != null) {
            return of(mapper.apply(value));
        }

        return empty();
    }

    public Value<T> ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }

        return this;
    }

    public Value<T> ifNotPresent(Runnable runnable) {
        if (value == null) {
            runnable.run();
        }

        return this;
    }

    public <U> Value<U> flatMap(Function<? super T, Value<U>> mapper) {
        if (value != null) {
            return Objects.requireNonNull(mapper.apply(value));
        }

        return empty();
    }

    public Value<T> filter(Predicate<? super T> predicate) {
        if (value != null) {
            if (predicate.test(value)) {
                return this;
            }
        }

        return empty();
    }

    public T orElse(T other) {
        if (value == null) {
            return other;
        }

        return value;
    }

    public T orElseGet(Supplier<? extends T> otherSupplier) {
        if (value == null) {
            return otherSupplier.get();
        }

        return value;
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> throwableSupplier) throws X {
        if (value == null) {
            throw throwableSupplier.get();
        }

        return value;
    }

}
