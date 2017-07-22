package com.github.windchopper.common.util.bean;

import java.util.Optional;
import java.util.function.Function;

public class PropertyCopier<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType, PreviousProcessingType, NextProcessingType> {

    @FunctionalInterface protected interface Preprocessor<SourcePropertyType, TargetPropertyType, PreviousProcessingType, NextProcessingType> {
        NextProcessingType preprocess(SourcePropertyType sourceProperty, TargetPropertyType targetProperty, PreviousProcessingType previousState);
    }

    protected final PropertyDescriptor<SourceBeanType, SourcePropertyType> sourcePropertyDescriptor;
    protected final PropertyDescriptor<TargetBeanType, TargetPropertyType> targetPropertyDescriptor;

    protected final PropertyCopier<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType, ?, PreviousProcessingType> previousCopier;
    protected final Preprocessor<SourcePropertyType, TargetPropertyType, PreviousProcessingType, NextProcessingType> preprocessor;

    protected PropertyCopier(PropertyDescriptor<SourceBeanType, SourcePropertyType> sourcePropertyDescriptor,
                             PropertyDescriptor<TargetBeanType, TargetPropertyType> targetPropertyDescriptor,
                             PropertyCopier<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType, ?, PreviousProcessingType> previousCopier,
                             Preprocessor<SourcePropertyType, TargetPropertyType, PreviousProcessingType, NextProcessingType> preprocessor) {
        this.sourcePropertyDescriptor = sourcePropertyDescriptor;
        this.targetPropertyDescriptor = targetPropertyDescriptor;
        this.previousCopier = previousCopier;
        this.preprocessor = preprocessor;
    }

    protected NextProcessingType applyPreprocessor(SourcePropertyType sourceProperty, TargetPropertyType targetProperty) {
        return preprocessor.preprocess(sourceProperty, targetProperty, Optional.ofNullable(previousCopier)
            .map(processor -> processor.applyPreprocessor(sourceProperty, targetProperty))
            .orElse(null));
    }

    public static <SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType> PropertyCopier<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType, SourcePropertyType, SourcePropertyType> of(
            PropertyDescriptor<SourceBeanType, SourcePropertyType> sourcePropertyDescriptor,
            PropertyDescriptor<TargetBeanType, TargetPropertyType> targetPropertyDescriptor) {
        return new PropertyCopier<>(sourcePropertyDescriptor, targetPropertyDescriptor, null,
            (sourceProperty, targetProperty, previousState) -> sourceProperty);
    }

    public static <SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType> void copy(SourceBeanType sourceBean, TargetBeanType targetBean, PropertyCopier<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType, ?, TargetPropertyType> copier) {
        copier.targetPropertyDescriptor.setPropertyState(targetBean, copier.applyPreprocessor(
            copier.sourcePropertyDescriptor.getPropertyState(sourceBean), copier.targetPropertyDescriptor.getPropertyState(targetBean)));
    }

    public <N> PropertyCopier<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType, NextProcessingType, N> convert(Function<NextProcessingType, N> converter) {
        return new PropertyCopier<>(sourcePropertyDescriptor, targetPropertyDescriptor,
            this, (sourceProperty, targetProperty, previousState) -> converter.apply(previousState));
    }

    public PropertyCopier<SourceBeanType, SourcePropertyType, TargetBeanType, TargetPropertyType, NextProcessingType, NextProcessingType> replace() {
        return new PropertyCopier<>(sourcePropertyDescriptor, targetPropertyDescriptor,
            this, (sourceProperty, targetProperty, previousState) -> previousState);
    }

}
