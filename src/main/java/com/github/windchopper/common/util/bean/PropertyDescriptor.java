package com.github.windchopper.common.util.bean;

public abstract class PropertyDescriptor<BeanType, PropertyType> {

    @FunctionalInterface public interface PropertyGetter<BeanType, PropertyType> {
        PropertyType get(BeanType bean);
    }

    @FunctionalInterface public interface PropertySetter<BeanType, PropertyType> {
        void set(BeanType bean, PropertyType value);
    }

    protected final PropertyGetter<BeanType, PropertyType> propertyGetter;
    protected final PropertySetter<BeanType, PropertyType> propertySetter;

    protected PropertyDescriptor(PropertyGetter<BeanType, PropertyType> propertyGetter,
                                 PropertySetter<BeanType, PropertyType> propertySetter) {
        this.propertyGetter = propertyGetter;
        this.propertySetter = propertySetter;
    }

    protected PropertyType get(BeanType bean) {
        return propertyGetter.get(bean);
    }

    protected void set(BeanType bean, PropertyType value) {
        propertySetter.set(bean, value);
    }

    public <TargetBeanType, TargetPropertyType> void copy(BeanType sourceBean,
                                                          TargetBeanType targetBean,
                                                          PropertyDescriptor<TargetBeanType, TargetPropertyType> targetPropertyDescriptor,
                                                          PropertyCopyStrategy<BeanType, PropertyType, TargetBeanType, TargetPropertyType>... copyStrategies) {

    }

}
