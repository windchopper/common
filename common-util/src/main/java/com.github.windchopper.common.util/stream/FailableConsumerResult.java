package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FailableConsumerResult<T> extends FailableResult<T> {

    private final T value;

    public FailableConsumerResult(T value, Throwable exception) {
        super(exception);
        this.value = value;
    }

    @Override public Optional<T> result() {
        return Optional.ofNullable(value);
    }

    public void onSuccess(Consumer<T> handler) {
        if (exception == null) {
            handler.accept(value);
        }
    }

    public void onFailure(BiConsumer<T, Throwable> handler) {
        if (exception != null) {
            handler.accept(value, exception);
        }
    }

    public void recover(BiConsumer<T, Throwable> recoverer) {
        if (exception != null) {
            recoverer.accept(value, exception);
        }
    }

}
