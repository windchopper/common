package com.github.windchopper.common.cdi;

import jakarta.enterprise.inject.spi.InjectionPoint;

import java.lang.annotation.Annotation;
import java.util.Optional;

public abstract class Producer {

    protected <A extends Annotation> Optional<A> findQualifier(InjectionPoint injectionPoint, Class<A> qualifierType) {
        return Optional.ofNullable(injectionPoint.getAnnotated())
            .map(annotated -> annotated.getAnnotation(qualifierType))
            .or(() -> injectionPoint.getQualifiers().stream()
                .filter(qualifierType::isInstance)
                .map(qualifierType::cast)
                .findAny());
    }

}
