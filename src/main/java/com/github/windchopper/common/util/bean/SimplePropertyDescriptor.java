package com.github.windchopper.common.util.bean;

public class SimplePropertyDescriptor<BeanType, PropertyType> extends PropertyDescriptor<BeanType, PropertyType> {

    protected SimplePropertyDescriptor(Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        super(getter, setter);
    }

}
