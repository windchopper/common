package name.wind.common.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class SystemProperty<T> implements Supplier<Optional<T>> {

    private final String name;
    private final Function<String, T> transformer;

    public SystemProperty(String name, Function<String, T> transformer) {
        this.name = name;
        this.transformer = transformer;
    }

    @Override public Optional<T> get() {
        return Optional.ofNullable(System.getProperty(name))
            .map(transformer);
    }

}
