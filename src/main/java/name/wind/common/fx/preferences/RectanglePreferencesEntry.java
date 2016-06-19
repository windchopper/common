package name.wind.common.fx.preferences;

import javafx.geometry.Rectangle2D;
import name.wind.common.preferences.StructuredPreferencesEntry;
import name.wind.common.util.Builder;

public class RectanglePreferencesEntry extends StructuredPreferencesEntry<Rectangle2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";
    private static final String KEY__WIDTH = "width";
    private static final String KEY__HEIGHT = "height";

    private static final String DEF_ANY = "0.0";

    public RectanglePreferencesEntry(Class<?> invoker, String name) {
        super(
            invoker,
            name,
            structuredValue -> new Rectangle2D(
                Double.parseDouble(structuredValue.getOrDefault(KEY__X, DEF_ANY)),
                Double.parseDouble(structuredValue.getOrDefault(KEY__Y, DEF_ANY)),
                Double.parseDouble(structuredValue.getOrDefault(KEY__WIDTH, DEF_ANY)),
                Double.parseDouble(structuredValue.getOrDefault(KEY__HEIGHT, DEF_ANY))),
            rectangle -> Builder.direct(() -> new StructuredValue(name))
                .set(target -> value -> target.put(KEY__X, value), String.valueOf(rectangle.getMinX()))
                .set(target -> value -> target.put(KEY__Y, value), String.valueOf(rectangle.getMinY()))
                .set(target -> value -> target.put(KEY__WIDTH, value), String.valueOf(rectangle.getWidth()))
                .set(target -> value -> target.put(KEY__HEIGHT, value), String.valueOf(rectangle.getHeight()))
                .get());
    }

}
