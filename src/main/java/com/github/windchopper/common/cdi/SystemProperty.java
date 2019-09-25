package com.github.windchopper.common.cdi;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, CONSTRUCTOR, FIELD }) @Retention(RUNTIME) @Qualifier @Documented public @interface SystemProperty {

    @Nonbinding String value();

}
