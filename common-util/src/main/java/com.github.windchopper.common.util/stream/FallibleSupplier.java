package com.github.windchopper.common.util.stream;

@FunctionalInterface public interface FallibleSupplier<T> extends Fallible {

    T get() throws Throwable;

}
