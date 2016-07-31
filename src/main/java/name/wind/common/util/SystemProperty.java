package name.wind.common.util;

import java.util.function.Function;

public class SystemProperty<T> {

    private final String name;
    private final Function<String, T> transformer;
    private final Buffered<T> bufferedValue;

    public SystemProperty(String name, Function<String, T> transformer) {
        this.name = name;
        this.transformer = transformer;
        this.bufferedValue = new Buffered<>(this::rawValue);
    }

    private T rawValue() {
        return transformer.apply(System.getProperty(name));
    }

    public T value() {
        return bufferedValue.get();
    }

    public T value(T defaultValue) {
        T value = bufferedValue.get();

        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

}
