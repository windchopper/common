package com.github.windchopper.common.cdi;

import jakarta.enterprise.inject.spi.CDI;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public class BeanReference<T> {

    private final Class<T> type;
    private final Annotation[] qualifiers;

    public BeanReference(Class<T> type, Annotation... qualifiers) {
        this.type = type;
        this.qualifiers = qualifiers;
    }

    public T resolve() {
        return CDI.current()
            .select(type, qualifiers)
            .get();
    }

    public Stream<T> resolveAll() {
        return CDI.current()
            .select(type, qualifiers)
            .stream();
    }

}
