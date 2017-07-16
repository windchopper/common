package com.github.windchopper.common.util.bean;

import static java.util.Arrays.stream;

public class BeanCopier<B1, B2> {

    private final B1 sourceBean;
    private final B2 targetBean;

    private BeanCopier(B1 sourceBean, B2 targetBean) {
        this.sourceBean = sourceBean;
        this.targetBean = targetBean;
    }

    public static <SourceBeanType, TargetBeanType> BeanCopier<SourceBeanType, TargetBeanType> of(SourceBeanType sourceBean, TargetBeanType targetBean) {
        return new BeanCopier<>(sourceBean, targetBean);
    }

    @SafeVarargs
    public final void copy(PropertyHandler<B1, ?, B2, ?, ?, ?>... propertyHandlers) {
        for (PropertyHandler<B1, ?, B2, ?, ?, ?> propertyHandler : propertyHandlers) {
            propertyHandler.apply(sourceBean, targetBean);
        }
    }

}
