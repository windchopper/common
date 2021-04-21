package com.github.windchopper.common.util.stream;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Fallible {

    static <T, E extends Throwable> void rethrow(Function<Throwable, E> faultMapper, FallibleRunnable fallibleRunnable) throws E {
        try {
            fallibleRunnable.run();
        } catch (Throwable thrown) {
            throw faultMapper.apply(thrown);
        }
    }

    static <T, E extends Throwable> T rethrow(Function<Throwable, E> faultMapper, FallibleSupplier<T> fallibleSupplier) throws E {
        try {
            return fallibleSupplier.get();
        } catch (Throwable thrown) {
            throw faultMapper.apply(thrown);
        }
    }

    static <T, E extends Throwable> void rethrow(T value, Function<Throwable, E> faultMapper, FallibleConsumer<T> fallibleSupplier) throws E {
        try {
            fallibleSupplier.accept(value);
        } catch (Throwable thrown) {
            throw faultMapper.apply(thrown);
        }
    }

    static Supplier<FallibleRunnableResult> infallible(FallibleRunnable fallibleRunnable) {
        return () -> {
            try {
                fallibleRunnable.run();

                return new SuccessfulRunnableResult() {
                };
            } catch (Throwable thrown) {
                return (FailedRunnableResult) () -> thrown;
            }
        };
    }

    static <T> Supplier<FallibleSupplierResult<T>> infallible(FallibleSupplier<T> fallibleSupplier) {
        return () -> {
            try {
                T suppliedValue = fallibleSupplier.get();

                return (SuccessfulSupplierResult<T>) () -> Optional.ofNullable(suppliedValue);
            } catch (Throwable thrown) {
                return (FailedSupplierResult<T>) () -> thrown;
            }
        };
    }

    static <I, O> Function<I, FallibleFunctionResult<I, O>> infallible(FallibleFunction<I, O> fallibleFunction) {
        return incomingValue -> {
            try {
                O outgoingValue = fallibleFunction.apply(incomingValue);

                return new SuccessfulFunctionResult<>() {

                    @Override public Optional<I> incomingValue() {
                        return Optional.ofNullable(incomingValue);
                    }

                    @Override public Optional<O> outgoingValue() {
                        return Optional.ofNullable(outgoingValue);
                    }

                };
            } catch (Throwable thrown) {
                return new FailedFunctionResult<>() {

                    @Override public Optional<I> incomingValue() {
                        return Optional.ofNullable(incomingValue);
                    }

                    @Override public Throwable fault() {
                        return thrown;
                    }

                };
            }
        };
    }

}
