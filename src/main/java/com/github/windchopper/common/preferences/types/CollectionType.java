package com.github.windchopper.common.preferences.types;

import javax.json.JsonObject;
import java.util.Collection;
import java.util.function.Function;

public abstract class CollectionType<T, C extends Collection<T>> extends StructuralType<C> {

    protected static long counter = 0;

    public CollectionType(Function<JsonObject, C> transformer, Function<C, JsonObject> reverseTransformer) {
        super(transformer, reverseTransformer);
    }

}
