package ru.wind.common.fx.builder;

import java.util.function.Supplier;

public abstract class AbstractBuilder<T, B extends AbstractBuilder<T, B>> extends AbstractLazyBuilder<T, B> {

    protected final T target;

    protected AbstractBuilder(Supplier<T> targetSupplier) {
        target = targetSupplier.get();
    }

    @Override public T get() {
        return target;
    }

}
