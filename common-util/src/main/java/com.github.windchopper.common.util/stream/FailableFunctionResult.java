package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FailableFunctionResult<I, O> extends FailableResult<O> {

    private final I value;
    private final O outcome;

    public FailableFunctionResult(I value, O outcome, Throwable exception) {
        super(exception);
        this.value = value;
        this.outcome = outcome;
    }

    @Override public Optional<O> result() {
        return Optional.ofNullable(outcome);
    }

    public FailableFunctionResult<I, O> onSuccess(BiConsumer<I, O> handler) {
        if (exception == null) {
            handler.accept(value, outcome);
        }

        return this;
    }

    public FailableFunctionResult<I, O> onFailure(BiConsumer<I, Throwable> handler) {
        if (exception != null) {
            handler.accept(value, exception);
        }

        return this;
    }

    public O recover(BiFunction<I, Throwable, O> recoverer) {
        return exception != null ? recoverer.apply(value, exception) : outcome;
    }

}