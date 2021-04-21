package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public interface FallibleResult<F extends FailedResult<F, S>, S extends SuccessfulResult<F, S>> {

    default boolean failed() {
        return false;
    }

    default void ifFailed(Consumer<F> failedResultConsumer) {

    }

    default Optional<F> failedResult() {
        return Optional.empty();
    }

    default boolean successful() {
        return false;
    }

    default void ifSuccessful(Consumer<S> successfulResultConsumer) {

    }

    default Optional<S> successfulResult() {
        return Optional.empty();
    }

}
