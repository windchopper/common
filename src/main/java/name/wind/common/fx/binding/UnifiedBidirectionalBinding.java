package name.wind.common.fx.binding;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;
import java.util.function.Function;

public class UnifiedBidirectionalBinding<Type1st, Type2nd> {

    private final WeakReference<Property<Type1st>> reference1st;
    private final WeakReference<Property<Type2nd>> reference2nd;

    private final Function<Type1st, Type2nd> directTransformer;
    private final Function<Type2nd, Type1st> returnTransformer;

    private boolean updateInProgress;

    private UnifiedBidirectionalBinding(Property<Type1st> property1st,
                                        Property<Type2nd> property2nd,
                                        Function<Type1st, Type2nd> directTransformer,
                                        Function<Type2nd, Type1st> returnTransformer) {
        reference1st = new WeakReference<>(property1st);
        reference2nd = new WeakReference<>(property2nd);
        this.directTransformer = directTransformer;
        this.returnTransformer = returnTransformer;
    }

    private <T> void updateProperty(Property<T> property, T value) {
        if (property != null) {
            property.setValue(value);
        }
    }

    private void changed1st(ObservableValue<? extends Type1st> observable, Type1st oldValue, Type1st newValue) {
        if (updateInProgress) {
            return;
        }

        updateInProgress = true;

        try {
            updateProperty(
                reference2nd.get(),
                directTransformer.apply(newValue));
        } finally {
            updateInProgress = false;
        }
    }

    private void changed2nd(ObservableValue<? extends Type2nd> observable, Type2nd oldValue, Type2nd newValue) {
        if (updateInProgress) {
            return;
        }

        updateInProgress = true;

        try {
            updateProperty(
                reference1st.get(),
                returnTransformer.apply(newValue));
        } finally {
            updateInProgress = false;
        }
    }

    /*
     *
     */

    public static <Type1st, Type2nd> UnifiedBidirectionalBinding<Type1st, Type2nd> bindBidirectional(Property<Type1st> property1st,
                                                                                                     Property<Type2nd> property2nd,
                                                                                                     Function<Type1st, Type2nd> directTransformer,
                                                                                                     Function<Type2nd, Type1st> returnTransformer) {
        UnifiedBidirectionalBinding<Type1st, Type2nd> binding = new UnifiedBidirectionalBinding<>(
            property1st, property2nd, directTransformer, returnTransformer);

        property1st.setValue(returnTransformer.apply(property2nd.getValue()));

        property1st.addListener(binding::changed1st);
        property2nd.addListener(binding::changed2nd);

        return binding;
    }

}
