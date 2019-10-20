package com.github.windchopper.common.util.stream;

import java.util.Optional;

public abstract class FailableResult<T> {

    protected final Throwable exception;

    public FailableResult(Throwable exception) {
        this.exception = exception;
    }

    public abstract Optional<T> result();

    public boolean succeeded() {
        return exception == null;
    }

    public boolean failed() {
        return exception != null;
    }

}
