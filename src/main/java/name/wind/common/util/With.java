package name.wind.common.util;

import java.util.function.Consumer;

public class With<T> extends Value<T> {

    private With(T value) {
        super(value);
    }

    public static <T> With<T> of(T value) {
        return new With<>(value);
    }

    public With<T> perform(Consumer<T> consumer) {
        consumer.accept(value);
        return this;
    }

}
