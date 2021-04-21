package com.github.windchopper.common.util.stream;

import java.util.Optional;

public interface SuccessfulSupplierResult<T> extends FallibleSupplierResult<T>, SuccessfulResult<FailedSupplierResult<T>, SuccessfulSupplierResult<T>> {

    Optional<T> suppliedValue();

}
