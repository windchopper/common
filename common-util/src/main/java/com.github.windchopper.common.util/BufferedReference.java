package com.github.windchopper.common.util;

import com.github.windchopper.common.util.stream.FailableSupplier;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;

public class BufferedReference<T, E extends Throwable> implements Reference<T, E> {

    private final Duration lifeTime;
    private final FailableSupplier<T, E> valueSupplier;

    private Instant previousSupplyTime = Instant.MIN;
    private T suppliedValue;

    public BufferedReference(FailableSupplier<T, E> valueSupplier) {
        this(ChronoUnit.FOREVER.getDuration(), valueSupplier);
    }

    public BufferedReference(Duration lifeTime, FailableSupplier<T, E> valueSupplier) {
        this.lifeTime = requireNonNull(lifeTime, "lifeTime");
        this.valueSupplier = requireNonNull(valueSupplier, "valueSupplier");
    }

    @Override public T get() throws E {
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

    @Override public void invalidate() {
        previousSupplyTime = Instant.MIN;
        suppliedValue = null;
    }

}
