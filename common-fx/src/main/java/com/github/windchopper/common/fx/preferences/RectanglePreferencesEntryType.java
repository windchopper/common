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
                Double.parseDouble(source.getString(KEY__X)),
                Double.parseDouble(source.getString(KEY__Y)),
                Double.parseDouble(source.getString(KEY__WIDTH)),
                Double.parseDouble(source.getString(KEY__HEIGHT))),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .set(target -> value -> target.add(KEY__X, value), String.valueOf(source.getMinX()))
                .set(target -> value -> target.add(KEY__Y, value), String.valueOf(source.getMinY()))
                .set(target -> value -> target.add(KEY__WIDTH, value), String.valueOf(source.getWidth()))
                .set(target -> value -> target.add(KEY__HEIGHT, value), String.valueOf(source.getHeight()))
                .map(JsonObjectBuilder::build)
                .get());
    }

}
