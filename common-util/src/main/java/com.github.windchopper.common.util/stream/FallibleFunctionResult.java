package com.github.windchopper.common.util.stream;

import java.util.Optional;

public interface FallibleFunctionResult<I, O> extends FallibleResult<FailedFunctionResult<I, O>, SuccessfulFunctionResult<I, O>> {

    Optional<I> incomingValue();

}
