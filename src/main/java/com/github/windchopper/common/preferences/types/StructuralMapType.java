package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.util.Pipeliner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toMap;

public class StructuralMapType<T, M extends Map<String, T>> extends StructuralType<M> {

    public StructuralMapType(Supplier<M> mapSupplier, Function<JsonObject, T> transformer, Function<T, JsonObject> reverseTransformer) {
        super(
            source -> source.entrySet().stream()
                .collect(toMap(
                    Map.Entry::getKey,
                    entry -> transformer.apply((JsonObject) entry.getValue()),
                    MapType::duplicate,
                    mapSupplier)),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .accept(jsonObjectBuilder -> source.entrySet()
                    .forEach(entry -> jsonObjectBuilder.add(entry.getKey(), reverseTransformer.apply(entry.getValue()))))
                .map(JsonObjectBuilder::build)
                .get());
    }

}
