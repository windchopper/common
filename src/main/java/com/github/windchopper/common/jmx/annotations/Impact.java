package com.github.windchopper.common.jmx.annotations;

import javax.management.DescriptorKey;
import javax.management.MBeanOperationInfo;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Impact {

    String DESCRIPTOR_KEY__IMPACT = "impactValue";

    int INFO = MBeanOperationInfo.INFO;
    int ACTION = MBeanOperationInfo.ACTION;
    int ACTION_INFO = MBeanOperationInfo.ACTION_INFO;
    int UNKNOWN = MBeanOperationInfo.UNKNOWN;

    @DescriptorKey(DESCRIPTOR_KEY__IMPACT)
    int value();

}
