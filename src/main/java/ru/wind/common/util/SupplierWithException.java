package ru.wind.common.util;

@FunctionalInterface public interface SupplierWithException<T, E extends Exception> {

    T supply() throws E;

}
