package com.github.windchopper.common.fx.binding;

import javafx.beans.property.Property;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class AddionalBindings {

    public static <T> TransformingBidirectionalBinding<T, T> bindBidirectionally(@Nonnull Property<T> property1st,
                                                                                 @Nonnull Property<T> property2nd) {
        return bindBidirectionally(property1st, property2nd, Function.identity(), Function.identity());
    }

    public static <Type1st, Type2nd> TransformingBidirectionalBinding<Type1st, Type2nd>
        bindBidirectionally(@Nonnull Property<Type1st> property1st,
                            @Nonnull Property<Type2nd> property2nd,
                            @Nonnull Function<Type1st, Type2nd> transformer,
                            @Nonnull Function<Type2nd, Type1st> reverseTransformer) {
        return new TransformingBidirectionalBinding<>(property1st, property2nd, transformer, reverseTransformer);
    }

}
