package com.github.windchopper.common.util.bean;

public class AtomicSimplePropertyDescriptor<BeanType, PropertyType> extends PropertyDescriptor<BeanType, PropertyType> {

    protected AtomicSimplePropertyDescriptor(Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        super(getter, setter);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> of(
            Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        return new AtomicSimplePropertyDescriptor<>(getter, setter);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> of(
            Getter<BeanType, PropertyType> getter) {
        return of(getter, null);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> of(
            Setter<BeanType, PropertyType> setter) {
        return of(null, setter);
    }

}
