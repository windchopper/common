package ru.wind.common.util.function;

@FunctionalInterface public interface Supplier<T, E extends Exception> {

    T supply() throws E;

}
