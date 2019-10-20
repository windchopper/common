package com.github.windchopper.common.util.bean;

import java.util.Collection;

public class CollectionPropertyDescriptor<BeanType, ElementType, CollectionType extends Collection<ElementType>> extends PropertyDescriptor<BeanType, CollectionType> {

    protected CollectionPropertyDescriptor(Getter<BeanType, CollectionType> getter, Setter<BeanType, CollectionType> setter) {
        super(getter, setter);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> functional(
            Getter<BeanType, CollectionType> getter, Setter<BeanType, CollectionType> setter) {
        return new CollectionPropertyDescriptor<>(getter, setter);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> functional(
            Getter<BeanType, CollectionType> getter) {
        return functional(getter, null);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> functional(
            Setter<BeanType, CollectionType> setter) {
        return functional(null, setter);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> reflective(
            Class<BeanType> beanType, String propertyName, Class<CollectionType> propertyType) throws ReflectiveOperationException {
        return new CollectionPropertyDescriptor<>(
            new ReflectiveGetter<>(beanType, propertyName, propertyType),
            new ReflectiveSetter<>(beanType, propertyName, propertyType));
    }

}
