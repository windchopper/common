package com.github.windchopper.common.util.bean;

public class PropertyCopier<SourceBeanType, TargetBeanType> {

    protected final SourceBeanType sourceBean;
    protected final TargetBeanType targetBean;

    protected PropertyCopier(SourceBeanType sourceBean, TargetBeanType targetBean) {
        this.sourceBean = sourceBean;
        this.targetBean = targetBean;
    }

    public static <SourceBeanType, TargetBeanType> PropertyCopier<SourceBeanType, TargetBeanType> of(
            SourceBeanType sourceBean, TargetBeanType targetBean) {
        return new PropertyCopier<>(sourceBean, targetBean);
    }

    public PropertyCopier<SourceBeanType, TargetBeanType> copying(
            PropertyDescriptor sourcePropertyDescriptor, PropertyDescriptor targetPropertyDescriptor) {
        return this;
    }

    public PropertyCopier<SourceBeanType, TargetBeanType> use(
        PropertyCopyStrategy copyStrategy) {
        return this;
    }

}
