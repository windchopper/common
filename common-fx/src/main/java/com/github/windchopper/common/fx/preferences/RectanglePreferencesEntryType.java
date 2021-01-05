package com.github.windchopper.common.fx.preferences;

import com.github.windchopper.common.preferences.PreferencesEntryStructuralType;
import com.github.windchopper.common.preferences.types.DoubleType;
import javafx.geometry.Rectangle2D;

import java.util.Map;
import java.util.Optional;

public class RectanglePreferencesEntryType extends PreferencesEntryStructuralType<Rectangle2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";
    private static final String KEY__WIDTH = "width";
    private static final String KEY__HEIGHT = "height";

    public RectanglePreferencesEntryType() {
        super(
            Map.of(
                KEY__X, new DoubleType(),
                KEY__Y, new DoubleType(),
                KEY__WIDTH, new DoubleType(),
                KEY__HEIGHT, new DoubleType()),
            values -> new Rectangle2D(
                Optional.ofNullable(values.get(KEY__X))
                    .map(Double.class::cast)
                    .orElse(0D),
                Optional.ofNullable(values.get(KEY__Y))
                    .map(Double.class::cast)
                    .orElse(0D),
                Optional.ofNullable(values.get(KEY__WIDTH))
                    .map(Double.class::cast)
                    .orElse(0D),
                Optional.ofNullable(values.get(KEY__HEIGHT))
                    .map(Double.class::cast)
                    .orElse(0D)),
            rectangle -> Map.of(
                KEY__X, String.valueOf(rectangle.getMinX()),
                KEY__Y, String.valueOf(rectangle.getMinY()),
                KEY__WIDTH, String.valueOf(rectangle.getWidth()),
                KEY__HEIGHT, String.valueOf(rectangle.getHeight())));
    }

}
