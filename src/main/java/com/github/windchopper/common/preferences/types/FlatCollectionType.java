package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.util.Pipeliner;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toCollection;

public class FlatCollectionType<T, C extends Collection<T>> extends CollectionType<T, C> {

    public FlatCollectionType(Supplier<C> collectionSupplier, FlatType<T> flatType) {
        this(collectionSupplier, flatType.transformer, flatType.reverseTransformer);
    }

    public FlatCollectionType(Supplier<C> collectionSupplier, Function<String, T> transformer, Function<T, String> reverseTransformer) {
        super(
            source -> source.values().stream()
                .map(jsonValue -> transformer.apply(((JsonString) jsonValue).getString()))
                .collect(toCollection(collectionSupplier)),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .accept(jsonObjectBuilder -> source.stream()
                    .map(reverseTransformer)
                    .forEach(value -> jsonObjectBuilder.add(String.valueOf(++index), value)))
                .map(JsonObjectBuilder::build)
                .get());
    }

}
