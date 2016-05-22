package ru.wind.common.util.function;

@FunctionalInterface public interface Performer<E extends Exception> {

    void perform() throws E;

}
