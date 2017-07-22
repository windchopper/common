package com.github.windchopper.common.util.bean;

import java.util.Collection;

public class CollectionPropertyDescriptor<BeanType, ElementType, CollectionType extends Collection<ElementType>> extends PropertyDescriptor<BeanType, CollectionType> {

    protected CollectionPropertyDescriptor(Getter<BeanType, CollectionType> getter, Setter<BeanType, CollectionType> setter) {
        super(getter, setter);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> of(
            Getter<BeanType, CollectionType> getter, Setter<BeanType, CollectionType> setter) {
        return new CollectionPropertyDescriptor<>(getter, setter);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> of(
            Getter<BeanType, CollectionType> getter) {
        return of(getter, null);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> of(
            Setter<BeanType, CollectionType> setter) {
        return of(null, setter);
    }

}
