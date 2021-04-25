package com.github.windchopper.common.fx.binding;

import javafx.beans.property.Property;

import java.util.function.Function;

public class AdditionalBindings {

    public static <T> TransformingBidirectionalBinding<T, T> bindBidirectionally(
        Property<T> property1st, Property<T> property2nd) {

        return new TransformingBidirectionalBinding<>(
            property1st,
            property2nd,
            Function.identity(),
            Function.identity());
    }

    public static <L, R> TransformingBidirectionalBinding<L, R> bindBidirectionally(
        Property<L> property1st, Property<R> property2nd, Function<L, R> transformer, Function<R, L> reverseTransformer) {

        return new TransformingBidirectionalBinding<>(
            property1st,
            property2nd,
            transformer,
            reverseTransformer);
    }

}
