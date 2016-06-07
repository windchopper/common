package ru.wind.common.fx.builder;

import java.util.function.Supplier;

public abstract class AbstractLazyBuilder<T, B extends AbstractLazyBuilder<T, B>> implements Supplier<T> {

    @SuppressWarnings("unchecked") protected B self() {
        return (B) this;
    }

}
