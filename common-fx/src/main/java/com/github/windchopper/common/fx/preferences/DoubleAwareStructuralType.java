package com.github.windchopper.common.fx.preferences;

import com.github.windchopper.common.preferences.types.StructuralType;

import javax.json.JsonObject;
import javax.json.JsonString;
import java.util.Optional;
import java.util.function.Function;

abstract class DoubleAwareStructuralType<T> extends StructuralType<T> {

    DoubleAwareStructuralType(Function<JsonObject, T> transformer, Function<T, JsonObject> reverseTransformer) {
        super(transformer, reverseTransformer);
    }

    static double readDouble(JsonObject jsonObject, String key) {
        return Optional.ofNullable(jsonObject.getJsonString(key))
            .map(JsonString::getString)
            .map(Double::parseDouble)
            .orElse(0.0);
    }

}
