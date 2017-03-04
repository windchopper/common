package com.github.windchopper.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
public @interface BundleString {

    String bundleKey();
    String bundleLocation();

}
