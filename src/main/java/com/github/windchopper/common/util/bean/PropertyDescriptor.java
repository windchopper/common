package com.github.windchopper.common.util.bean;

import java.util.Objects;

public abstract class PropertyDescriptor<BeanType, PropertyType> {

    @FunctionalInterface public interface PropertyGetter<BeanType, PropertyType> {
        PropertyType get(BeanType bean);
    }

    @FunctionalInterface public interface PropertySetter<BeanType, PropertyType> {
        void set(BeanType bean, PropertyType value);
    }

    protected final PropertyGetter<BeanType, PropertyType> propertyGetter;
    protected final PropertySetter<BeanType, PropertyType> propertySetter;

    protected PropertyDescriptor(PropertyGetter<BeanType, PropertyType> propertyGetter,
                                 PropertySetter<BeanType, PropertyType> propertySetter) {
        this.propertyGetter = propertyGetter;
        this.propertySetter = propertySetter;
    }

    protected PropertyType getPropertyState(BeanType bean) {
        return Objects.requireNonNull(propertyGetter, "Getter not defined").get(bean);
    }

    protected void setPropertyState(BeanType bean, PropertyType value) {
        Objects.requireNonNull(propertySetter, "Setter not defined").set(bean, value);
    }

}
