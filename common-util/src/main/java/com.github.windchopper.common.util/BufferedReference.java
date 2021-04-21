package com.github.windchopper.common.util;

import com.github.windchopper.common.util.stream.FallibleSupplier;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class BufferedReference<T> implements Reference<T> {

    private final Duration lifeTime;
    private final FallibleSupplier<T> valueSupplier;

    private Instant previousSupplyTime = Instant.MIN;
    private T suppliedValue;

    public BufferedReference(FallibleSupplier<T> valueSupplier) {
        this(ChronoUnit.FOREVER.getDuration(), valueSupplier);
    }

    public BufferedReference(Duration lifeTime, FallibleSupplier<T> valueSupplier) {
        this.lifeTime = lifeTime;
        this.valueSupplier = valueSupplier;
    }

    @Override public T get() throws Throwable {
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
