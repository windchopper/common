package com.github.windchopper.common.jmx;

import com.github.windchopper.common.jmx.annotations.Description;
import com.github.windchopper.common.jmx.annotations.Impact;
import com.github.windchopper.common.jmx.annotations.Name;

import javax.management.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AnnotatedMBean extends StandardMBean {

    public <T> AnnotatedMBean(T managementBeanImplementation, Class<T> managementBeanInterface, boolean isMXBean) {
        super(managementBeanImplementation, managementBeanInterface, isMXBean);
    }

    protected String valueOrResourceFromBundle(DescriptorRead descriptorRead, String value, String resourceKey, String resourceBundleName) {
        value = (String) descriptorRead.getDescriptor().getFieldValue(value);
        resourceBundleName = (String) descriptorRead.getDescriptor().getFieldValue(resourceBundleName);
        resourceKey = (String) descriptorRead.getDescriptor().getFieldValue(resourceKey);

        if (resourceBundleName != null && resourceBundleName.length() > 0) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName);

            if (resourceKey != null && resourceKey.length() > 0) {
                return resourceBundle.getString(resourceKey);
            }
        }

        return value;
    }

    @Override
    protected String getDescription(MBeanInfo info) {
        return valueOrResourceFromBundle(
            info,
            Description.DESCRIPTOR_KEY__DESCRIPTION,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME);
    }

    @Override
    protected String getDescription(MBeanAttributeInfo attributeInfo) {
        return valueOrResourceFromBundle(
            attributeInfo,
            Description.DESCRIPTOR_KEY__DESCRIPTION,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME);
    }

    @Override
    protected String getDescription(MBeanConstructorInfo constructorInfo) {
        return valueOrResourceFromBundle(
            constructorInfo,
            Description.DESCRIPTOR_KEY__DESCRIPTION,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME);
    }

    @Override
    protected String getDescription(MBeanConstructorInfo constructorInfo, MBeanParameterInfo parameterInfo, int sequence) {
        return valueOrResourceFromBundle(
            parameterInfo,
            Description.DESCRIPTOR_KEY__DESCRIPTION,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME);
    }

    @Override
    protected String getDescription(MBeanOperationInfo operationInfo) {
        return valueOrResourceFromBundle(
            operationInfo,
            Description.DESCRIPTOR_KEY__DESCRIPTION,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME);
    }

    @Override
    protected String getDescription(MBeanFeatureInfo featureInfo) {
        return valueOrResourceFromBundle(
            featureInfo,
            Description.DESCRIPTOR_KEY__DESCRIPTION,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME);
    }

    @Override
    protected String getDescription(MBeanOperationInfo operationInfo, MBeanParameterInfo parameterInfo, int sequence) {
        return valueOrResourceFromBundle(
            parameterInfo,
            Description.DESCRIPTOR_KEY__DESCRIPTION,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_KEY,
            Description.DESCRIPTOR_KEY__DESCRIPTION_RESOURCE_BUNDLE_NAME);
    }

    @Override
    protected String getParameterName(MBeanConstructorInfo constructorInfo, MBeanParameterInfo parameterInfo, int sequence) {
        return (String) parameterInfo.getDescriptor().getFieldValue(Name.DESCRIPTOR_KEY__NAME);
    }

    @Override
    protected String getParameterName(MBeanOperationInfo operationInfo, MBeanParameterInfo parameterInfo, int sequence) {
        return (String) parameterInfo.getDescriptor().getFieldValue(Name.DESCRIPTOR_KEY__NAME);
    }

    @Override
    protected int getImpact(MBeanOperationInfo operationInfo) {
        return Optional.ofNullable(operationInfo.getDescriptor().getFieldValue(Impact.DESCRIPTOR_KEY__IMPACT))
            .map(Integer.class::cast).orElse(0);
    }

}
