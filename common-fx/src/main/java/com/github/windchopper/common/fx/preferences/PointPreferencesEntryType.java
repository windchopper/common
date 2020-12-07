package com.github.windchopper.common.fx.preferences;

import com.github.windchopper.common.util.Pipeliner;
import javafx.geometry.Point2D;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class PointPreferencesEntryType extends DoubleAwareStructuralType<Point2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";

    public PointPreferencesEntryType() {
        super(
            source -> new Point2D(
                readDouble(source, KEY__X),
                readDouble(source, KEY__Y)),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .set(target -> value -> target.add(KEY__X, value), String.valueOf(source.getX()))
                .set(target -> value -> target.add(KEY__Y, value), String.valueOf(source.getY()))
                .map(JsonObjectBuilder::build)
                .get());
    }

}
