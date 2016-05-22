package ru.wind.common.util;

import ru.wind.common.util.function.Consumer;
import ru.wind.common.util.function.Supplier;

public final class WithSupport {

    public static <T, E extends Exception> void with(Supplier<T, E> objectSupplier, Consumer<T, E> consumer) throws E {
        consumer.accept(objectSupplier.supply());
    }

}
