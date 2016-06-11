package name.wind.common.fx.preferences;

import javafx.geometry.Rectangle2D;
import name.wind.common.preferences.StructuredPreferencesEntry;
import name.wind.common.util.Builder;

public class RectanglePreferencesEntry extends StructuredPreferencesEntry<Rectangle2D> {

    public RectanglePreferencesEntry(Class<?> invoker, String name) {
        super(
            invoker,
            name,
            structuredValue -> new Rectangle2D(
                Double.parseDouble(structuredValue.get("x")),
                Double.parseDouble(structuredValue.get("y")),
                Double.parseDouble(structuredValue.get("width")),
                Double.parseDouble(structuredValue.get("height"))),
            rectangle -> Builder.direct(() -> new StructuredValue(name))
                .set(target -> value -> target.put("x", value), String.valueOf(rectangle.getMinX()))
                .set(target -> value -> target.put("y", value), String.valueOf(rectangle.getMinY()))
                .set(target -> value -> target.put("width", value), String.valueOf(rectangle.getWidth()))
                .set(target -> value -> target.put("height", value), String.valueOf(rectangle.getHeight()))
                .get());
    }

}
