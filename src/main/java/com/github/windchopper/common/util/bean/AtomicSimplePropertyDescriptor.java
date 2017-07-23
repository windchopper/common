package com.github.windchopper.common.util.bean;

public class AtomicSimplePropertyDescriptor<BeanType, PropertyType> extends SimplePropertyDescriptor<BeanType, PropertyType> {

    protected AtomicSimplePropertyDescriptor(Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        super(getter, setter);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> functional(
            Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        return new AtomicSimplePropertyDescriptor<>(getter, setter);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> functional(
            Getter<BeanType, PropertyType> getter) {
        return functional(getter, null);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> functional(
            Setter<BeanType, PropertyType> setter) {
        return functional(null, setter);
    }

    public static <BeanType, PropertyType> AtomicSimplePropertyDescriptor<BeanType, PropertyType> reflective(
            Class<BeanType> beanType, String propertyName, Class<PropertyType> propertyType) throws ReflectiveOperationException {
        return functional(
            new ReflectiveGetter<>(beanType, propertyName, propertyType),
            new ReflectiveSetter<>(beanType, propertyName, propertyType));
    }

}
