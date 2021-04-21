package com.github.windchopper.common.util.stream;

public interface FallibleSupplier<T> extends Fallible {

    T get() throws Throwable;

}
