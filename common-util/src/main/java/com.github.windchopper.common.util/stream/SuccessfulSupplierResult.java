package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public interface SuccessfulSupplierResult<T> extends FallibleSupplierResult<T>, SuccessfulResult<FailedSupplierResult<T>, SuccessfulSupplierResult<T>> {

    @Override default void ifSuccessful(Consumer<SuccessfulSupplierResult<T>> successfulResultConsumer) {
        successfulResultConsumer.accept(this);
    }

    @Override default Optional<SuccessfulSupplierResult<T>> successfulResult() {
        return Optional.of(this);
    }

    Optional<T> suppliedValue();

}
