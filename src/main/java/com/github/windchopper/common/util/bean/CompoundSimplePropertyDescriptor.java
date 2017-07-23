package com.github.windchopper.common.util.bean;

public class CompoundSimplePropertyDescriptor<BeanType, PropertyType> extends SimplePropertyDescriptor<BeanType, PropertyType> {

    protected CompoundSimplePropertyDescriptor(Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        super(getter, setter);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> functional(
            Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        return new CompoundSimplePropertyDescriptor<>(getter, setter);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> functional(
            Getter<BeanType, PropertyType> getter) {
        return functional(getter, null);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> functional(
            Setter<BeanType, PropertyType> setter) {
        return functional(null, setter);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> reflective(
            Class<BeanType> beanType, String propertyName, Class<PropertyType> propertyType) throws ReflectiveOperationException {
        return new CompoundSimplePropertyDescriptor<>(
            new ReflectiveGetter<>(beanType, propertyName, propertyType),
            new ReflectiveSetter<>(beanType, propertyName, propertyType));
    }

}