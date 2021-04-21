package com.github.windchopper.common.util.stream;

@FunctionalInterface public interface FallibleConsumer<T> extends Fallible {

    void accept(T value) throws Throwable;

}
