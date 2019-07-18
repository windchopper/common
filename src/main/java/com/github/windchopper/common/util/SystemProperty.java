package com.github.windchopper.common.util;

import java.util.Optional;
import java.util.function.Function;

import static java.util.function.Function.identity;

public enum SystemProperty {

    OS_ARCHITECTURE("os.arch"),
    OS_VERSION("os.version"),
    OS_NAME("os.name"),
    USER_HOME("user.home"),
    USER_NAME("user.name");

    private final String property;

    SystemProperty(String property) {
        this.property = property;
    }

    public Optional<String> read() {
        return read(identity());
    }

    public <T> Optional<T> read(Function<String, T> converter) {
        return Optional.of(property)
            .map(System::getProperty)
            .map(converter);
    }

}
