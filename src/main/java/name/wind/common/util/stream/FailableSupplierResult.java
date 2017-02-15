package name.wind.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class FailableSupplierResult<T> {

    private final T value;
    private final Throwable exception;

    public FailableSupplierResult(T value, Throwable exception) {
        this.value = value;
        this.exception = exception;
    }

    public boolean succeeded() {
        return exception == null;
    }

    public boolean failed() {
        return exception != null;
    }

    public void onSuccess(Consumer<T> handler) {
        if (exception == null) {
            handler.accept(value);
        }
    }

    public void onFailure(Consumer<Throwable> handler) {
        if (exception != null) {
            handler.accept(exception);
        }
    }

    public T recover(Function<Throwable, T> recoverer) {
        return exception != null ? recoverer.apply(exception) : value;
    }

    public Optional<T> optional() {
        return Optional.ofNullable(value);
    }

}
