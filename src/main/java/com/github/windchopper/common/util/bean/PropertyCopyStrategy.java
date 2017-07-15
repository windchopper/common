package com.github.windchopper.common.util.bean;

public interface PropertyCopyStrategy<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType> {

    void apply(SourceBeanType sourceBean,
               PropertyDescriptor<SourceBeanType, SourcePropertyType> sourcePropertyDescriptor,
               TargetBeanType targetBean,
               PropertyDescriptor<TargetBeanType, TargetPropertyType> targetPropertyDescriptor);

    static PropertyCopyStrategy convert(Object o) {
        return null;
    }

    static PropertyCopyStrategy override() {
        return null;
    }

    static PropertyCopyStrategy replace() {
        return null;
    }
}
