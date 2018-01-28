package com.github.windchopper.common.fx.binding;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class TransformingBidirectionalBinding<Type1st, Type2nd> {

    private final WeakReference<Property<Type1st>> reference1st;
    private final WeakReference<Property<Type2nd>> reference2nd;

    private final Function<Type1st, Type2nd> transformer;
    private final Function<Type2nd, Type1st> reverseTransformer;

    private final Lock updateLock = new ReentrantLock();

    public TransformingBidirectionalBinding(@Nonnull Property<Type1st> property1st,
                                            @Nonnull Property<Type2nd> property2nd,
                                            @Nonnull Function<Type1st, Type2nd> transformer,
                                            @Nonnull Function<Type2nd, Type1st> reverseTransformer) {
        reference1st = new WeakReference<>(requireNonNull(property1st));
        reference2nd = new WeakReference<>(requireNonNull(property2nd));

        this.transformer = requireNonNull(transformer);
        this.reverseTransformer = requireNonNull(reverseTransformer);

        property1st.setValue(reverseTransformer.apply(property2nd.getValue()));

        property1st.addListener(this::changed1st);
        property2nd.addListener(this::changed2nd);
    }

    private <T> void exclusiveUpdate(Property<T> targetProperty, T value) {
        if (targetProperty != null && updateLock.tryLock())
            try {
                targetProperty.setValue(value);
            } finally {
                updateLock.unlock();
            }
    }

    @SuppressWarnings("unused") private void changed1st(ObservableValue<? extends Type1st> observable, Type1st oldValue, Type1st newValue) {
        exclusiveUpdate(reference2nd.get(), transformer.apply(newValue));
    }

    @SuppressWarnings("unused") private void changed2nd(ObservableValue<? extends Type2nd> observable, Type2nd oldValue, Type2nd newValue) {
        exclusiveUpdate(reference1st.get(), reverseTransformer.apply(newValue));
    }

}
