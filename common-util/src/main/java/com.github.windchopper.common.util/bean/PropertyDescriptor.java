package com.github.windchopper.common.util.bean;

import java.lang.invoke.*;
import java.util.Optional;

public abstract class PropertyDescriptor<BeanType, PropertyType> {

    @FunctionalInterface public interface Getter<BeanType, PropertyType> {
        PropertyType get(BeanType bean);
    }

    @FunctionalInterface public interface Setter<BeanType, PropertyType> {
        void set(BeanType bean, PropertyType value);
    }

    protected static class ReflectiveGetter<BeanType, PropertyType> implements Getter<BeanType, PropertyType> {

        private final MethodHandle handle;

        public ReflectiveGetter(Class<BeanType> beanType, String propertyName, Class<PropertyType> propertyType) throws ReflectiveOperationException {
            handle = MethodHandles.lookup().findVirtual(beanType, methodName(propertyName, propertyType), MethodType.methodType(propertyType));
        }

        private String methodName(String propertyName, Class<PropertyType> propertyType) {
            return propertyType == boolean.class ? "is" : "get" + Character.toUpperCase(propertyName.charAt(0)) +
                propertyName.substring(1);
        }

        @SuppressWarnings("unchecked") @Override public PropertyType get(BeanType bean) {
            try {
                return (PropertyType) handle.invoke(bean);
            } catch (Throwable thrown) {
                throw new RuntimeException(thrown);
            }
        }

    }

    protected static class ReflectiveSetter<BeanType, PropertyType> implements Setter<BeanType, PropertyType> {

        private final MethodHandle handle;

        public ReflectiveSetter(Class<BeanType> beanType, String propertyName, Class<PropertyType> propertyType) throws ReflectiveOperationException {
            handle = MethodHandles.lookup().findVirtual(beanType, methodName(propertyName), MethodType.methodType(void.class, propertyType));
        }

        private String methodName(String propertyName) {
            return "set" + Character.toUpperCase(propertyName.charAt(0)) +
                propertyName.substring(1);
        }

        @Override public void set(BeanType bean, PropertyType value) {
            try {
                handle.invoke(bean, value);
            } catch (Throwable thrown) {
                throw new RuntimeException(thrown);
            }
        }

    }

    protected final Getter<BeanType, PropertyType> getter;
    protected final Setter<BeanType, PropertyType> setter;

    protected PropertyDescriptor(Getter<BeanType, PropertyType> getter, Setter<BeanType, PropertyType> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public PropertyType getPropertyState(BeanType bean) {
        return Optional.ofNullable(getter).map(getter -> getter.get(bean))
            .orElse(null);
    }

    public void setPropertyState(BeanType bean, PropertyType value) {
        setter.set(bean, value);
    }

}
