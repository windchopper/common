package ru.wind.common.util;

public final class WithSupport {

    public static <T, E extends Exception> void with(SupplierWithException<T, E> objectSupplier, ConsumerWithException<T, E> consumer) throws E {
        consumer.accept(objectSupplier.supply());
    }

}
