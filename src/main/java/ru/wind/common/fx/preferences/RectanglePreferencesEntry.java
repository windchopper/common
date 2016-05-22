package ru.wind.common.fx.preferences;

import javafx.geometry.Rectangle2D;
import ru.wind.common.preferences.StructuredPreferencesEntry;
import ru.wind.common.util.Builder;

import static java.lang.Double.parseDouble;
import static java.lang.String.valueOf;

public class RectanglePreferencesEntry extends StructuredPreferencesEntry<Rectangle2D> {

    public RectanglePreferencesEntry(Class<?> invoker, String name) {
        super(
            invoker,
            name,
            structuredValue -> new Rectangle2D(
                parseDouble(structuredValue.get("x")),
                parseDouble(structuredValue.get("y")),
                parseDouble(structuredValue.get("width")),
                parseDouble(structuredValue.get("height"))),
            rectangle -> new Builder<>(() -> new StructuredValue(name))
                .accept(structuredValue -> {
                    structuredValue.put("x", valueOf(rectangle.getMinX()));
                    structuredValue.put("y", valueOf(rectangle.getMinY()));
                    structuredValue.put("width", valueOf(rectangle.getWidth()));
                    structuredValue.put("height", valueOf(rectangle.getHeight()));
                })
                .get());
    }

}
