package com.github.windchopper.common.util.stream;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FailableConsumerResult<T> implements FailableResult<T> {

    private final T value;
    private final Throwable exception;

    public FailableConsumerResult(T value, Throwable exception) {
        this.value = value;
        this.exception = exception;
    }

    @Override public boolean succeeded() {
        return exception == null;
    }

    @Override public boolean failed() {
        return exception != null;
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
