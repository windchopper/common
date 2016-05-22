package ru.wind.common.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class WithSupport {

    public static <T> void with(Supplier<T> objectSupplier, Consumer<T> consumer) {
        consumer.accept(objectSupplier.get());
    }

}
