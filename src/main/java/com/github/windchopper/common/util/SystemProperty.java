package com.github.windchopper.common.util;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class SystemProperty<T> implements Supplier<Optional<T>> {

    private final String name;
    private final Function<String, T> transformer;

    public SystemProperty(@Nonnull String name, @Nonnull Function<String, T> transformer) {
        this.name = requireNonNull(name);
        this.transformer = requireNonNull(transformer);
    }

    @Override public Optional<T> get() {
        return Optional.ofNullable(System.getProperty(name))
            .map(transformer);
    }

}
