package com.github.windchopper.common.fx.preferences;

import com.github.windchopper.common.preferences.types.StructuralType;
import com.github.windchopper.common.util.Pipeliner;
import javafx.geometry.Point2D;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class PointPreferencesEntryType extends StructuralType<Point2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";

    public PointPreferencesEntryType() {
        super(
            source -> new Point2D(
                source.getJsonNumber(KEY__X).doubleValue(),
                source.getJsonNumber(KEY__Y).doubleValue()),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .set(target -> value -> target.add(KEY__X, value), source.getX())
                .set(target -> value -> target.add(KEY__Y, value), source.getY())
                .map(JsonObjectBuilder::build)
                .get());
    }

}
