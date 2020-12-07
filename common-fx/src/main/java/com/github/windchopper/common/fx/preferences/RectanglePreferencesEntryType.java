package com.github.windchopper.common.fx.preferences;

import com.github.windchopper.common.preferences.types.StructuralType;
import com.github.windchopper.common.util.Pipeliner;
import javafx.geometry.Rectangle2D;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class RectanglePreferencesEntryType extends StructuralType<Rectangle2D> {

    private static final String KEY__X = "x";
    private static final String KEY__Y = "y";
    private static final String KEY__WIDTH = "width";
    private static final String KEY__HEIGHT = "height";

    public RectanglePreferencesEntryType() {
        super(
            source -> new Rectangle2D(
                source.getJsonNumber(KEY__X).doubleValue(),
                source.getJsonNumber(KEY__Y).doubleValue(),
                source.getJsonNumber(KEY__WIDTH).doubleValue(),
                source.getJsonNumber(KEY__HEIGHT).doubleValue()),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .set(target -> value -> target.add(KEY__X, value), source.getMinX())
                .set(target -> value -> target.add(KEY__Y, value), source.getMinY())
                .set(target -> value -> target.add(KEY__WIDTH, value), source.getWidth())
                .set(target -> value -> target.add(KEY__HEIGHT, value), source.getHeight())
                .map(JsonObjectBuilder::build)
                .get());
    }

}
