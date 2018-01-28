package com.github.windchopper.common.cdi.temporary;

import javax.enterprise.context.NormalScope;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NormalScope @Inherited @Documented @Retention(RUNTIME) @Target({
    TYPE, METHOD, FIELD
}) public @interface TemporaryScoped {
}
