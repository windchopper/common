package ru.wind.common.util.function;

@FunctionalInterface public interface Function<I, O, E extends Exception> {

    O apply(I input) throws E;

}
