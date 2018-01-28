package com.github.windchopper.common.jmx.annotations;

import javax.management.DescriptorKey;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Name {

    String DESCRIPTOR_KEY__NAME = "nameValue";

    @DescriptorKey(DESCRIPTOR_KEY__NAME)
    String value();

}
