package com.github.windchopper.common.util.bean;

import java.util.Objects;
import java.util.Optional;

public abstract class PropertyDescriptor<BeanType, PropertyType> {

    @FunctionalInterface public interface Getter<BeanType, PropertyType> {
        PropertyType get(BeanType bean);
    }

    @FunctionalInterface public interface Setter<BeanType, PropertyType> {
        void set(BeanType bean, PropertyType value);
    }

    protected final Getter<BeanType, PropertyType> getter;
    protected final Setter<BeanType, PropertyType> setter;

    protected PropertyDescriptor(Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public PropertyType getPropertyState(BeanType bean) {
        return Optional.ofNullable(getter).map(getter -> getter.get(bean))
            .orElse(null);
    }

    public void setPropertyState(BeanType bean, PropertyType value) {
        Objects.requireNonNull(setter, "Setter not defined")
            .set(bean, value);
    }

}
