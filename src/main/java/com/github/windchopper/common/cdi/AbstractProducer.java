package com.github.windchopper.common.cdi;

import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.annotation.Annotation;
import java.util.Optional;

public class AbstractProducer {

    protected <A extends Annotation> A findQualifier(InjectionPoint injectionPoint, Class<A> qualifierType) {
        return Optional.ofNullable(injectionPoint.getAnnotated())
            .map(annotated -> annotated.getAnnotation(qualifierType))
            .orElse(injectionPoint.getQualifiers().stream()
                .filter(qualifierType::isInstance)
                .map(qualifierType::cast)
                .findAny()
                .orElse(null));
    }

}
