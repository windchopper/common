package com.github.windchopper.common.util.stream;

import java.util.function.Function;

@FunctionalInterface public interface FailableSupplier<T, E extends Throwable> {

    T get() throws Throwable;

    static <T, E extends Throwable> Function<T, FailableSupplierResult<T>> wrap(FailableSupplier<T, E> failableSupplier) {
        return value -> failsafeGet(failableSupplier);
    }

    static <T, E extends Throwable> FailableSupplierResult<T> failsafeGet(FailableSupplier<T, E> failableSupplier) {
        try {
            return new FailableSupplierResult<>(failableSupplier.get(), null);
        } catch (Throwable thrown) {
            return new FailableSupplierResult<>(null, thrown);
        }
    }

}
