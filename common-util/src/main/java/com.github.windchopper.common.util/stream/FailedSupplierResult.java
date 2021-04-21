package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public interface FailedSupplierResult<T> extends FallibleSupplierResult<T>, FailedResult<FailedSupplierResult<T>, SuccessfulSupplierResult<T>> {

    @Override default void ifFailed(Consumer<FailedSupplierResult<T>> failedResultConsumer) {
        failedResultConsumer.accept(this);
    }

    @Override default Optional<FailedSupplierResult<T>> failedResult() {
        return Optional.of(this);
    }

}
