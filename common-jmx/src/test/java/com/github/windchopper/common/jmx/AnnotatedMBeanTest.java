package com.github.windchopper.common.jmx;

import org.junit.jupiter.api.Test;

import javax.management.IntrospectionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnotatedMBeanTest {

    @Test public void test() throws IntrospectionException {
        var sample = new Sample();
        var annotatedManagementBean = new AnnotatedMBean(sample, SampleMBean.class, false);
        var managementBeanInfo = annotatedManagementBean.getMBeanInfo();

        assertEquals("Sample bean", managementBeanInfo.getDescription());
        assertEquals("Sample bean test operation", managementBeanInfo.getOperations()[0].getDescription());
        assertEquals("Number of elephants", managementBeanInfo.getAttributes()[0].getDescription());
    }

}
