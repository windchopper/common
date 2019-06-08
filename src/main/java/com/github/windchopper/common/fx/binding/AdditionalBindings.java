package com.github.windchopper.common.fx.binding;

import javafx.beans.property.Property;

import java.util.function.Function;

public class AdditionalBindings {

    public static <T> TransformingBidirectionalBinding<T, T> bindBidirectionally(Property<T> property1st, Property<T> property2nd) {
        return bindBidirectionally(property1st, property2nd, Function.identity(), Function.identity());
    }

    public static <Type1st, Type2nd> TransformingBidirectionalBinding<Type1st, Type2nd>
        bindBidirectionally(Property<Type1st> property1st,
                            Property<Type2nd> property2nd,
                            Function<Type1st, Type2nd> transformer,
                            Function<Type2nd, Type1st> reverseTransformer) {
        return new TransformingBidirectionalBinding<>(property1st, property2nd, transformer, reverseTransformer);
    }

}
