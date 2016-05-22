package ru.wind.common.util;

import java.util.function.Supplier;

public class WithSupport {

    @FunctionalInterface public interface Operator<T> {
        void operate(T object);
    }

    @FunctionalInterface public interface OperatorWithException<T, E extends Exception> {
        void operate(T object) throws E;
    }

    public static <T> void with(Supplier<T> objectSupplier, Operator<T> operator) {
        operator.operate(objectSupplier.get());
    }

    public static <T, E extends Exception> void withException(Supplier<T> objectSupplier, OperatorWithException<T, E> operatorWithException) throws E {
        operatorWithException.operate(objectSupplier.get());
    }

}
