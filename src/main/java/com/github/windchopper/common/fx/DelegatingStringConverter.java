package com.github.windchopper.common.fx;

import javafx.util.StringConverter;

import java.util.function.Function;

public class DelegatingStringConverter<T> extends StringConverter<T> {

    private final Function<T, String> toStringConverter;
    private final Function<String, T> fromStringConverter;

    public DelegatingStringConverter(Function<T, String> toStringConverter) {
        this(toStringConverter, string -> null);
    }

    public DelegatingStringConverter(Function<T, String> toStringConverter, Function<String, T> fromStringConverter) {
        this.toStringConverter = toStringConverter;
        this.fromStringConverter = fromStringConverter;
    }

    @Override
    public String toString(T object) {
        return toStringConverter.apply(object);
    }

    @Override
    public T fromString(String string) {
        return fromStringConverter.apply(string);
    }

}
