package name.wind.common.util;

import java.util.Optional;
import java.util.function.Function;

public class SystemProperty<T> {

    private final String name;
    private final Function<String, T> transformer;

    public SystemProperty(String name, Function<String, T> transformer) {
        this.name = name;
        this.transformer = transformer;
    }

    public String rawValue() {
        return System.getProperty(name);
    }

    public String rawValue(String defaultValue) {
        return System.getProperty(name, defaultValue);
    }

    public T transformedValue() {
        return Optional.ofNullable(rawValue()).map(transformer).orElse(null);
    }

    public T transformedValue(T defaultValue) {
        return Optional.ofNullable(rawValue()).map(transformer).orElse(defaultValue);
    }

}
