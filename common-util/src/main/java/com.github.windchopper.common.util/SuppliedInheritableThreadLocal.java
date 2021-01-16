package com.github.windchopper.common.util;

import java.util.function.Supplier;

public class SuppliedInheritableThreadLocal<T> extends InheritableThreadLocal<T> {

    private final Supplier<T> initialSupplier;

    public SuppliedInheritableThreadLocal(Supplier<T> initialSupplier) {
        this.initialSupplier = initialSupplier;
    }

    @Override protected T initialValue() {
        return initialSupplier.get();
    }

}
