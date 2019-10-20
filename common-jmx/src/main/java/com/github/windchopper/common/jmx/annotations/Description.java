package com.github.windchopper.common.jmx.annotations;

import javax.management.DescriptorKey;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.TYPE,
    ElementType.CONSTRUCTOR,
    ElementType.METHOD,
    ElementType.PARAMETER
})
public @interface Description {

    String DESCRIPTOR_KEY__DESCRIPTION = "descriptionValue";
    String DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY = "descriptionResourceKey";
    String DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME = "descriptionResourceBundleName";

    @DescriptorKey(DESCRIPTOR_KEY__DESCRIPTION)
    String value() default "";

    @DescriptorKey(DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY)
    String resourceKey() default "";

    @DescriptorKey(DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME)
    String resourceBundleName() default "";

}
