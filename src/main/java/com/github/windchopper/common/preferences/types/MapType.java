package com.github.windchopper.common.preferences.types;

import javax.json.JsonObject;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MapType<T, M extends Map<String, T>> extends StructuralType<M> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.preferences.i18n.messages");

    private static final String BUNDLE_KEY__DUPLICATE_KEY = "com.github.windchopper.common.preferences.MapType.duplicate";

    public MapType(Function<JsonObject, M> transformer, Function<M, JsonObject> reverseTransformer) {
        super(transformer, reverseTransformer);
    }

    static <K, V> K duplicate(K key, V value) {
        throw new IllegalArgumentException(
            String.format(
                bundle.getString(BUNDLE_KEY__DUPLICATE_KEY),
                key));
    }

}
