package com.github.windchopper.common.util.stream;

public interface FailedResult<F extends FailedResult<F, S>, S extends SuccessfulResult<F, S>> extends FallibleResult<F, S> {

    @Override default boolean failed() {
        return true;
    }

    Throwable fault();

}
