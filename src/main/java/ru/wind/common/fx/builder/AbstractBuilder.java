package ru.wind.common.fx.builder;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

import java.util.function.Function;
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

    public <V> B propertyChangeListener(Function<T, Property<V>> propertyAccessor, ChangeListener<V> changeListener) {
        propertyAccessor.apply(target).addListener(changeListener);
        return self();
    }

}
