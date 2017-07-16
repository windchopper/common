package com.github.windchopper.common.util.bean;

public class PropertyHandlerReplaceStrategy<P1, P2, T> implements PropertyHandlerStrategy<P1, P2, T, T> {

    @Override
    public T apply(P1 sourceProperty, P2 targetProperty, T previousState) {
        return previousState;
    }

}
