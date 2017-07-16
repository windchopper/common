package com.github.windchopper.common.util.bean;

import java.util.function.Function;

public class PropertyHandlerConversionStrategy<P1, P2, I, O> implements PropertyHandlerStrategy<P1, P2, I, O> {

    protected final Function<I, O> converter;

    protected PropertyHandlerConversionStrategy(Function<I, O> converter) {
        this.converter = converter;
    }

    @Override public O apply(P1 sourceProperty, P2 targetProperty, I previousState) {
        return converter.apply(previousState);
    }

}
