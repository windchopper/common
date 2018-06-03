package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.util.Pipeliner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toCollection;

public class StructuralCollectionType<T, C extends Collection<T>> extends CollectionType<T, C> {

    public StructuralCollectionType(Supplier<C> collectionSupplier, StructuralType<T> structuralType) {
        this(collectionSupplier, structuralType.transformer, structuralType.reverseTransformer);
    }

    public StructuralCollectionType(Supplier<C> collectionSupplier, Function<JsonObject, T> transformer, Function<T, JsonObject> reverseTransformer) {
        super(
            source -> source.values().stream()
                .map(value -> transformer.apply((JsonObject) value))
                .collect(toCollection(collectionSupplier)),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .accept(jsonObjectBuilder -> source.stream()
                    .map(reverseTransformer)
                    .forEach(value -> jsonObjectBuilder.add(String.valueOf(++index), value)))
                .map(JsonObjectBuilder::build)
                .get());
    }

}
