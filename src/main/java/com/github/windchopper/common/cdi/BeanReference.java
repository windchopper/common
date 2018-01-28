package com.github.windchopper.common.cdi;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import java.lang.annotation.Annotation;

public class BeanReference {

    private Class<?> type;
    private Annotation[] qualifiers;

    public BeanReference withType(Class<?> type) {
        this.type = type;
        return this;
    }

    public BeanReference withQualifiers(Annotation... qualifiers) {
        this.qualifiers = qualifiers;
        return this;
    }

    public Class<?> type() {
        return type;
    }

    public Annotation[] qualifiers() {
        return qualifiers;
    }

    @SuppressWarnings("unchecked") public Object resolve() {
        Instance<?> instance = null;

        if (type != null && qualifiers != null) {
            instance = CDI.current().select(type, qualifiers);
        } else if (type != null) {
            instance = CDI.current().select(type);
        } else if (qualifiers != null) {
            instance = CDI.current().select(qualifiers);
        }

        if (instance == null) {
            throw new IllegalStateException();
        } else {
            return instance.get();
        }
    }

}
