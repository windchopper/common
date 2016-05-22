package ru.wind.common.fx.builder;

import java.util.function.Supplier;

public abstract class AbstractBuilder<T, B extends AbstractBuilder<T, B>> implements Supplier<T> {

    protected final T target;

    protected AbstractBuilder(Supplier<T> targetSupplier) {
        target = targetSupplier.get();
    }

    @SuppressWarnings("unchecked") protected B self() {
        return (B) this;
    }

    @Override public T get() {
        return target;
    }

}
