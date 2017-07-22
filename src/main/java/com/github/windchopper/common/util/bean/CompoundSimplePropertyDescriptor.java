package com.github.windchopper.common.util.bean;

public class CompoundSimplePropertyDescriptor<BeanType, PropertyType> extends PropertyDescriptor<BeanType, PropertyType> {

    protected CompoundSimplePropertyDescriptor(Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        super(getter, setter);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> of(
            Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        return new CompoundSimplePropertyDescriptor<>(getter, setter);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> of(
            Getter<BeanType, PropertyType> getter) {
        return of(getter, null);
    }

    public static <BeanType, PropertyType> CompoundSimplePropertyDescriptor<BeanType, PropertyType> of(
            Setter<BeanType, PropertyType> setter) {
        return of(null, setter);
    }

}