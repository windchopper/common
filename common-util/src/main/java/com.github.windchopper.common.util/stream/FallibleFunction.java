package com.github.windchopper.common.util.stream;

public interface FallibleFunction<I, O> extends Fallible {

    O apply(I value) throws Throwable;

    static <T> FallibleFunction<T, T> identity() {
        return value -> value;
    }

}
