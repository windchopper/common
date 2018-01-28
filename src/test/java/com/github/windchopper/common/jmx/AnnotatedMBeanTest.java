package com.github.windchopper.common.jmx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.management.IntrospectionException;
import javax.management.MBeanInfo;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class AnnotatedMBeanTest {

    @Test
    public void test() throws IntrospectionException {
        Sample sample = new Sample();
        AnnotatedMBean annotatedManagementBean = new AnnotatedMBean(sample, SampleMBean.class, false);
        MBeanInfo managementBeanInfo = annotatedManagementBean.getMBeanInfo();

        assertEquals("Sample bean", managementBeanInfo.getDescription());
        assertEquals("Sample bean test operation", managementBeanInfo.getOperations()[0].getDescription());
        assertEquals("Number of elephants", managementBeanInfo.getAttributes()[0].getDescription());
    }

}