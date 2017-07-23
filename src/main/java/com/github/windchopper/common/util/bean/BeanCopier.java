package com.github.windchopper.common.util.bean;

public class BeanCopier<SourceBeanType, TargetBeanType> {

    private final SourceBeanType sourceBean;
    private final TargetBeanType targetBean;

    protected BeanCopier(SourceBeanType sourceBean, TargetBeanType targetBean) {
        this.sourceBean = sourceBean;
        this.targetBean = targetBean;
    }

    public static <SourceBeanType, TargetBeanType> BeanCopier<SourceBeanType, TargetBeanType> of(
            SourceBeanType sourceBean, TargetBeanType targetBean) {
        return new BeanCopier<>(sourceBean, targetBean);
    }

    //public static

}
