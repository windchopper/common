package com.github.windchopper.common.fx.preferences;

import com.github.windchopper.common.preferences.PreferencesEntryStructuralType;
import com.github.windchopper.common.preferences.types.DoubleType;
import javafx.geometry.Point2D;

import java.util.Map;
import java.util.Optional;

public class PointPreferencesEntryType extends PreferencesEntryStructuralType<Point2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";

    public PointPreferencesEntryType() {
        super(
            Map.of(
                KEY__X, new DoubleType(),
                KEY__Y, new DoubleType()),
            values -> new Point2D(
                Optional.ofNullable(values.get(KEY__X))
                    .map(Double.class::cast)
                    .orElse(0D),
                Optional.ofNullable(values.get(KEY__Y))
                    .map(Double.class::cast)
                    .orElse(0D)),
            point -> Map.of(
                KEY__X, point.getX(),
                KEY__Y, point.getY()));
    }

}
