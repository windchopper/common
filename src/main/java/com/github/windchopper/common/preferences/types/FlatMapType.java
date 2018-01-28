package com.github.windchopper.common.preferences.types;

import com.github.windchopper.common.util.Pipeliner;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toMap;

public class FlatMapType<T, M extends Map<String, T>> extends MapType<T, M> {

    public FlatMapType(Supplier<M> mapSupplier, Function<String, T> transformer, Function<T, String> reverseTransformer) {
        super(
            source -> source.entrySet().stream()
                .collect(toMap(
                    Map.Entry::getKey,
                    entry -> transformer.apply(((JsonString) entry.getValue()).getString()),
                    MapType::duplicate,
                    mapSupplier)),
            source -> Pipeliner.of(Json::createObjectBuilder)
                .accept(jsonObjectBuilder -> source.entrySet()
                    .forEach(entry -> jsonObjectBuilder.add(entry.getKey(), reverseTransformer.apply(entry.getValue()))))
                .map(JsonObjectBuilder::build)
                .get());
    }

}
