package com.github.windchopper.common.fx;

import javafx.util.StringConverter;

import java.util.Optional;
import java.util.function.Function;

import static java.util.function.Predicate.not;

public class FunctionalStringConverter<T> extends StringConverter<T> {

    private Function<T, String> toStringConverter = Object::toString;
    private Function<String, T> toObjectConverter = string -> {
        throw new UnsupportedOperationException();
    };

    public FunctionalStringConverter<T> convertingToString(Function<T, String> converter) {
        toStringConverter = converter;
        return this;
    }

    public FunctionalStringConverter<T> convertingToObject(Function<String, T> converter) {
        toObjectConverter = converter;
        return this;
    }

    @Override public String toString(T object) {
        return Optional.ofNullable(object)
            .map(toStringConverter)
            .orElse(null);
    }

    @Override public T fromString(String string) {
        return Optional.ofNullable(string)
            .filter(not(String::isBlank))
            .map(toObjectConverter)
            .orElse(null);
    }

}
