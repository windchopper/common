package com.github.windchopper.common.util.stream;

import java.util.function.Function;

@FunctionalInterface public interface FailableFunction<I, O, E extends Throwable> {

    O apply(I value) throws E;

    static <I, O, E extends Throwable> Function<I, FailableFunctionResult<I, O>> wrap(FailableFunction<I, O, E> failableFunction) {
        return value -> failsafeApply(value, failableFunction);
    }

    static <I, O, E extends Throwable> FailableFunctionResult<I, O> failsafeApply(I value, FailableFunction<I, O, E> failableFunction) {
        try {
            return new FailableFunctionResult<>(value, failableFunction.apply(value), null);
        } catch (Throwable thrown) {
            return new FailableFunctionResult<>(value, null, thrown);
        }
    }

}
