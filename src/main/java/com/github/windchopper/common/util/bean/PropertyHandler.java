package com.github.windchopper.common.util.bean;

import java.util.function.Function;

public class PropertyHandler<B1, P1, B2, P2, I, O> {

    protected final PropertyHandler<B1, P1, B2, P2, ?, I> previousHandler;
    protected final PropertyDescriptor<B1, P1> sourcePropertyDescriptor;
    protected final PropertyDescriptor<B2, P2> targetPropertyDescriptor;

    protected final PropertyHandlerStrategy<P1, P2, I, O> strategy;

    protected PropertyHandler(PropertyHandler<B1, P1, B2, P2, ?, I> previousHandler,
                              PropertyDescriptor<B1, P1> sourcePropertyDescriptor,
                              PropertyDescriptor<B2, P2> targetPropertyDescriptor,
                              PropertyHandlerStrategy<P1, P2, I, O> strategy) {
        this.previousHandler = previousHandler;
        this.sourcePropertyDescriptor = sourcePropertyDescriptor;
        this.targetPropertyDescriptor = targetPropertyDescriptor;
        this.strategy = strategy;
    }

    public static <B1, P1, B2, P2> PropertyHandler<B1, P1, B2, P2, P1, P1> of(
            PropertyDescriptor<B1, P1> sourcePropertyDescriptor,
            PropertyDescriptor<B2, P2> targetPropertyDescriptor) {
        return new PropertyHandler<>(null, sourcePropertyDescriptor, targetPropertyDescriptor,
            new PropertyHandlerGetStrategy<>());
    }

    public <N> PropertyHandler<B1, P1, B2, P2, O, N> convert(Function<O, N> converter) {
        return new PropertyHandler<>(this, sourcePropertyDescriptor, targetPropertyDescriptor, new PropertyHandlerConversionStrategy<>(converter));
    }

    public PropertyHandler<B1, P1, B2, P2, O, O> replace() {
        return new PropertyHandler<>(this, sourcePropertyDescriptor, targetPropertyDescriptor, new PropertyHandlerReplaceStrategy<>());
    }

    public O apply(B1 sourceBean, B2 targetBean) {
        I i = null;
        if (previousHandler != null)
            i = previousHandler.apply(sourceBean, targetBean);

        return strategy.apply(
            sourcePropertyDescriptor.getPropertyState(sourceBean),
            targetPropertyDescriptor.getPropertyState(targetBean),
            i);
    }

}
