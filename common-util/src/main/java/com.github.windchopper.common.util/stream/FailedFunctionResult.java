package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public interface FailedFunctionResult<I, O> extends FallibleFunctionResult<I, O>, FailedResult<FailedFunctionResult<I, O>, SuccessfulFunctionResult<I, O>> {

    @Override default void ifFailed(Consumer<FailedFunctionResult<I, O>> failedResultConsumer) {
        failedResultConsumer.accept(this);
    }

    @Override default Optional<FailedFunctionResult<I, O>> failedResult() {
        return Optional.of(this);
    }

}
