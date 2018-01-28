package com.github.windchopper.common.cdi.temporary;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@InterceptorBinding @Inherited @Documented @Retention(RUNTIME) @Target({
    TYPE, METHOD
}) public @interface WithTemporaryScope {
}
