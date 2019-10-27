package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class FailableSupplierResult<T> extends FailableResult<T> {

    private final T value;

    public FailableSupplierResult(T value, Throwable exception) {
        super(exception);
        this.value = value;
    }

    @Override public Optional<T> result() {
        return Optional.ofNullable(value);
    }

    public FailableSupplierResult<T> onSuccess(Consumer<T> handler) {
        if (exception == null) {
            handler.accept(value);
        }

        return this;
    }

    public FailableSupplierResult<T> onFailure(Consumer<Throwable> handler) {
        if (exception != null) {
            handler.accept(exception);
        }

        return this;
    }

    public T recover(Function<Throwable, T> recoverer) {
        return exception != null ? recoverer.apply(exception) : value;
    }

}