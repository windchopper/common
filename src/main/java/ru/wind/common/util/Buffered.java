package ru.wind.common.util;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Buffered<T, E extends Exception> {

    @FunctionalInterface public interface ValueFactory<T, E extends Exception> {
        T value() throws E;
    }

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");

    private final long lifetime;
    private final TimeUnit timeUnit;
    private final ValueFactory<T, E> valueSupplier;

    private long previousTimestamp;
    private T value;

    public Buffered(long lifetime, TimeUnit timeUnit, ValueFactory<T, E> valueSupplier) {
        this.lifetime = lifetime;
        this.timeUnit = Objects.requireNonNull(timeUnit, () -> bundle.getString("common.util.Buffered.nullParameter.timeUnit"));
        this.valueSupplier = Objects.requireNonNull(valueSupplier, () -> bundle.getString("common.util.Buffered.nullParameter.valueSupplier"));
    }

    public void invalidate() {
        value = null;
        previousTimestamp = 0;
    }

    public T value() throws E {
        long timestamp = System.currentTimeMillis();

        if (timestamp - previousTimestamp > timeUnit.toMillis(lifetime)) {
            value = null;
            previousTimestamp = timestamp;
        }

        if (value == null) {
            value = valueSupplier.value();
        }

        return value;
    }

}
