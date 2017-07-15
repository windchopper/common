package com.github.windchopper.common.util.bean;

import java.util.Collection;

public class CollectionPropertyDescriptor<BeanType, ElementType, CollectionType extends Collection<ElementType>> extends PropertyDescriptor<BeanType, CollectionType> {

    protected CollectionPropertyDescriptor(PropertyGetter propertyGetter, PropertySetter propertySetter) {
        super(propertyGetter, propertySetter);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> of(
            PropertyGetter<BeanType, CollectionType> propertyGetter, PropertySetter<BeanType, CollectionType> propertySetter) {
        return new CollectionPropertyDescriptor<>(propertyGetter, propertySetter);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> of(
            PropertyGetter<BeanType, CollectionType> propertyGetter) {
        return of(propertyGetter, null);
    }

    public static <BeanType, ElementType, CollectionType extends Collection<ElementType>> CollectionPropertyDescriptor<BeanType, ElementType, CollectionType> of(
            PropertySetter<BeanType, CollectionType> propertySetter) {
        return of(null, propertySetter);
    }

}
