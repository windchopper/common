package com.github.windchopper.common.fx.preferences;

import com.github.windchopper.common.preferences.PreferencesEntryStructuralType;
import com.github.windchopper.common.preferences.types.DoubleType;
import javafx.geometry.Point2D;

import java.util.Map;

public class PointPreferencesEntryType extends PreferencesEntryStructuralType<Point2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";

    public PointPreferencesEntryType() {
        super(
            Map.of(
                KEY__X, new DoubleType(),
                KEY__Y, new DoubleType()),
            values -> values.isEmpty() ? null : new Point2D(
                (Double) values.get(KEY__X),
                (Double) values.get(KEY__Y)),
            point -> Map.of(
                KEY__X, String.valueOf(point.getX()),
                KEY__Y, String.valueOf(point.getY())));
    }

}
