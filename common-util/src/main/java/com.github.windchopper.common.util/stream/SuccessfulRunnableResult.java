package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public interface SuccessfulRunnableResult extends FallibleRunnableResult, SuccessfulResult<FailedRunnableResult, SuccessfulRunnableResult> {

    @Override default void ifSuccessful(Consumer<SuccessfulRunnableResult> successfulResultConsumer) {
        successfulResultConsumer.accept(this);
    }

    @Override default Optional<SuccessfulRunnableResult> successfulResult() {
        return Optional.of(this);
    }

}
