package com.github.windchopper.common.util.bean;

import java.util.function.Supplier;

public class PropertyHandlerGetStrategy<P1, P2> implements PropertyHandlerStrategy<P1, P2, P1, P1> {

    @Override public P1 apply(P1 sourceProperty, P2 targetProperty, P1 previousState) {
        return sourceProperty;
    }

}
