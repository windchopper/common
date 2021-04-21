package com.github.windchopper.common.util.stream;

public interface FallibleSupplierResult<T> extends FallibleResult<FailedSupplierResult<T>, SuccessfulSupplierResult<T>> {
}
