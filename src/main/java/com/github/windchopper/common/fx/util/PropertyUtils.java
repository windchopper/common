package com.github.windchopper.common.fx.util;

import javafx.beans.property.Property;

import java.util.Objects;

public class PropertyUtils {

    public static <T> int valueHashCode(Property<T> property) {
        return property.getValue() == null ? 0 : property.getValue().hashCode();
    }

    public static <T> boolean valuesEquals(Property<T> property1st, Property<T> property2nd) {
        return property1st == property2nd || Objects.equals(property1st.getValue(), property2nd.getValue());
    }

}
