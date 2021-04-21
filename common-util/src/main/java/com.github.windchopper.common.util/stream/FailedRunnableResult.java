package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public interface FailedRunnableResult extends FallibleRunnableResult, FailedResult<FailedRunnableResult, SuccessfulRunnableResult> {

    @Override default void ifFailed(Consumer<FailedRunnableResult> failedResultConsumer) {
        failedResultConsumer.accept(this);
    }

    @Override default Optional<FailedRunnableResult> failedResult() {
        return Optional.of(this);
    }

}
