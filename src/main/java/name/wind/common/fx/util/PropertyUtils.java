package name.wind.common.fx.util;

import javafx.beans.property.Property;

public class PropertyUtils {

    public static <T> int hashCode(Property<T> property) {
        T value = property.getValue();
        return value != null
            ? value.hashCode()
            : 0;
    }

    public static <T> boolean equals(Property<T> property1st, Property<T> property2nd) {
        T value1st = property1st.getValue();
        T value2nd = property2nd.getValue();
        return value1st != null
            ? value1st.equals(value2nd)
            : value2nd == null;
    }

}
