package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FailableFunctionResult<I, O> implements FailableResult<O> {

    private final I value;
    private final O outcome;
    private final Throwable exception;

    public FailableFunctionResult(I value, O outcome, Throwable exception) {
        this.value = value;
        this.outcome = outcome;
        this.exception = exception;
    }

    @Override public boolean succeeded() {
        return exception == null;
    }

    @Override public boolean failed() {
        return exception != null;
    }

    public void onSuccess(BiConsumer<I, O> handler) {
        if (exception == null) {
            handler.accept(value, outcome);
        }
    }

    public void onFailure(BiConsumer<I, Throwable> handler) {
        if (exception != null) {
            handler.accept(value, exception);
        }
    }

    public O recover(BiFunction<I, Throwable, O> recoverer) {
        return exception != null ? recoverer.apply(value, exception) : outcome;
    }

    public Optional<O> optional() {
        return Optional.ofNullable(outcome);
    }

}
