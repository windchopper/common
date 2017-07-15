package com.github.windchopper.common.util.bean;

public class AtomicSimplePropertyDescriptor<BeanType, PropertyType> extends PropertyDescriptor<BeanType, PropertyType> {

    protected AtomicSimplePropertyDescriptor(PropertyGetter<BeanType, PropertyType> propertyGetter, PropertySetter<BeanType, PropertyType> propertySetter) {
        super(propertyGetter, propertySetter);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> of(
            PropertyGetter<BeanType, PropertyType> propertyGetter, PropertySetter<BeanType, PropertyType> propertySetter) {
        return new AtomicSimplePropertyDescriptor<>(propertyGetter, propertySetter);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> of(
            PropertyGetter<BeanType, PropertyType> propertyGetter) {
        return of(propertyGetter, null);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> of(
            PropertySetter<BeanType, PropertyType> propertySetter) {
        return of(null, propertySetter);
    }

}
