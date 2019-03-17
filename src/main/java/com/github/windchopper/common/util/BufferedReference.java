package com.github.windchopper.common.util;

import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class BufferedReference<T> implements Supplier<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.github.windchopper.common.i18n.messages");

    private static final String BUNDLE_KEY__NULL_PARAMETER = "com.github.windchopper.common.nullParameter";

    private final Duration lifeTime;
    private final Supplier<T> valueSupplier;

    private Instant previousSupplyTime = Instant.MIN;
    private T suppliedValue;

    public BufferedReference(Duration lifeTime, Supplier<T> valueSupplier) {
        this.lifeTime = requireNonNull(lifeTime, () -> String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "lifeTime"));
        this.valueSupplier = requireNonNull(valueSupplier, () -> String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "valueSupplier"));
    }

    public void invalidate() {
        previousSupplyTime = Instant.MIN;
        suppliedValue = null;
    }

    @Override public T get() {
        var now = Instant.now();

        if (Duration.between(previousSupplyTime, now).compareTo(lifeTime) > 0) {
            suppliedValue = null;
        }

        if (suppliedValue == null) {
            suppliedValue = valueSupplier.get();
            previousSupplyTime = now;
        }

        return suppliedValue;
    }

}
