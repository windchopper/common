package com.github.windchopper.common.util.bean;

public class CompoundSimplePropertyDescriptor<BeanType, PropertyType> extends PropertyDescriptor<BeanType, PropertyType> {

    protected CompoundSimplePropertyDescriptor(PropertyGetter<BeanType, PropertyType> propertyGetter, PropertySetter<BeanType, PropertyType> propertySetter) {
        super(propertyGetter, propertySetter);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> of(
            PropertyGetter<BeanType, PropertyType> propertyGetter, PropertySetter<BeanType, PropertyType> propertySetter) {
        return new CompoundSimplePropertyDescriptor<>(propertyGetter, propertySetter);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> of(
            PropertyGetter<BeanType, PropertyType> propertyGetter) {
        return of(propertyGetter, null);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> of(
            PropertySetter<BeanType, PropertyType> propertySetter) {
        return of(null, propertySetter);
    }

}
