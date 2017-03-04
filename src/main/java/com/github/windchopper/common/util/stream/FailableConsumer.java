package com.github.windchopper.common.util.stream;

import java.util.function.Function;

@FunctionalInterface public interface FailableConsumer<T, E extends Throwable> {

    void accept(T value) throws E;

    default T acceptAndReturn(T value) throws E {
        accept(value);
        return value;
    }

    static <T, E extends Throwable> Function<T, FailableConsumerResult<T>> wrap(FailableConsumer<T, E> failableConsumer) {
        return value -> failsafeAccept(value, failableConsumer);
    }

    @SuppressWarnings("unchecked")
    static <T, E extends Throwable> FailableConsumerResult<T> failsafeAccept(T value, FailableConsumer<T, E> failableConsumer) {
        try {
            return new FailableConsumerResult<>(failableConsumer.acceptAndReturn(value), null);
        } catch (Throwable thrown) {
            return new FailableConsumerResult<>(value, thrown);
        }
    }

}
