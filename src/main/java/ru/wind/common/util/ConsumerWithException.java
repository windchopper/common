package ru.wind.common.util;

@FunctionalInterface public interface ConsumerWithException<T, E extends Exception> {

    void accept(T value) throws E;

}
