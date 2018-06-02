package com.github.windchopper.common.fx.util;

import javafx.beans.property.Property;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PropertyUtils {

    public static <T> int valueHashCode(@Nonnull Property<T> property) {
        return property.getValue() == null ? 0 : property.getValue().hashCode();
    }

    public static <T> boolean valuesEquals(@Nonnull Property<T> property1st, @Nonnull Property<T> property2nd) {
        return property1st == property2nd || Objects.equals(property1st.getValue(), property2nd.getValue());
    }

}
