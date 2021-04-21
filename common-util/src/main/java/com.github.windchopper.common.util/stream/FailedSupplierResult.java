package com.github.windchopper.common.util.stream;

public interface FailedSupplierResult<T> extends FallibleSupplierResult<T>, FailedResult<FailedSupplierResult<T>, SuccessfulSupplierResult<T>> {
}
