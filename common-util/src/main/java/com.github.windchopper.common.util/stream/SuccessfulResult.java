package com.github.windchopper.common.util.stream;

public interface SuccessfulResult<F extends FailedResult<F, S>, S extends SuccessfulResult<F, S>> extends FallibleResult<F, S> {

    @Override default boolean successful() {
        return true;
    }

}
