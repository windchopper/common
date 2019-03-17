package com.github.windchopper.common.util.stream;

import java.util.Optional;

public class FailableRunnableResult extends FailableResult<Void> {

    public FailableRunnableResult(Throwable exception) {
        super(exception);
    }

    @Override public Optional<Void> result() {
        return Optional.empty();
    }

}
