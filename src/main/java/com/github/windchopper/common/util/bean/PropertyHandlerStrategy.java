package com.github.windchopper.common.util.bean;

public interface PropertyHandlerStrategy<P1, P2, I, O> {

    O apply(P1 sourceProperty, P2 targetProperty, I previousState);

}
