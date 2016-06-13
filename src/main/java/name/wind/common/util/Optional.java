package name.wind.common.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Optional<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Optional<?> empty = new Optional<>(null);

    private final T value;

    private Optional(T value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked") public static <T> Optional<T> empty() {
        return (Optional<T>) empty;
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") public static <T> Optional<T> convert(java.util.Optional<T> optional) {
        return new Optional<>(optional.orElse(null));
    }

    public boolean present() {
        return value != null;
    }

    public boolean notPresent() {
        return value == null;
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException(bundle.getString("common.util.Optional.nullValue"));
        }

        return value;
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        if (value != null) {
            return of(mapper.apply(value));
        }

        return empty();
    }

    public Optional<T> ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }

        return this;
    }

    public Optional<T> ifNotPresent(Runnable runnable) {
        if (value == null) {
            runnable.run();
        }

        return this;
    }

    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        if (value != null) {
            return Objects.requireNonNull(mapper.apply(value));
        }

        return empty();
    }

    public Optional<T> filter(Predicate<? super T> predicate) {
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
