package com.github.windchopper.common.fx.preferences;

import javafx.geometry.Point2D;
import com.github.windchopper.common.preferences.types.StructuralType;
import com.github.windchopper.common.util.Pipeliner;

import javax.json.Json;

public class PointPreferencesEntryType extends StructuralType<Point2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";

    public PointPreferencesEntryType() {
        super(
            source -> new Point2D(
                Double.parseDouble(source.getString(KEY__X)),
                Double.parseDouble(source.getString(KEY__Y))),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .set(target -> value -> target.add(KEY__X, value), String.valueOf(source.getX()))
                .set(target -> value -> target.add(KEY__Y, value), String.valueOf(source.getY()))
                .get().build());
    }

}
