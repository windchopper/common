package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public interface SuccessfulFunctionResult<I, O> extends FallibleFunctionResult<I, O>, SuccessfulResult<FailedFunctionResult<I, O>, SuccessfulFunctionResult<I, O>> {

    @Override default void ifSuccessful(Consumer<SuccessfulFunctionResult<I, O>> successfulResultConsumer) {
        successfulResultConsumer.accept(this);
    }

    @Override default Optional<SuccessfulFunctionResult<I, O>> successfulResult() {
        return Optional.of(this);
    }

    Optional<O> outgoingValue();

}
