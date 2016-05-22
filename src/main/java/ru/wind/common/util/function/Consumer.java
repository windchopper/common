package ru.wind.common.util.function;

@FunctionalInterface public interface Consumer<T, E extends Exception> {

    void accept(T value) throws E;

}
