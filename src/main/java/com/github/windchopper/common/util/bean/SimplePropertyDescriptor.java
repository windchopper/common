package com.github.windchopper.common.util.bean;

public abstract class SimplePropertyDescriptor<BeanType, PropertyType> extends PropertyDescriptor<BeanType, PropertyType> {

    protected SimplePropertyDescriptor(PropertyGetter<BeanType, PropertyType> propertyGetter, PropertySetter<BeanType, PropertyType> propertySetter) {
        super(propertyGetter, propertySetter);
    }

}
