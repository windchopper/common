package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Consumer;

public class FailableRunnableResult extends FailableResult<Void> {

    public FailableRunnableResult(Throwable exception) {
        super(exception);
    }

    @Override public Optional<Void> result() {
        return Optional.empty();
    }

    public FailableRunnableResult onSuccess(Runnable handler) {
        if (exception == null) {
            handler.run();
        }

        return this;
    }

    public FailableRunnableResult onFailure(Consumer<Throwable> handler) {
        if (exception != null) {
            handler.accept(exception);
        }

        return this;
    }

}
