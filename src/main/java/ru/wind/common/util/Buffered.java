package ru.wind.common.util;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Buffered<T, E extends Exception> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");

    private final long lifeTime;
    private final TimeUnit timeUnit;
    private final SupplierWithException<T, E> valueSupplier;
    private final ConsumerWithException<T, E> valueConsumer;

    private long previousSupplyTime;
    private T suppliedValue;

    public Buffered(SupplierWithException<T, E> valueSupplier) {
        this(Long.MAX_VALUE, TimeUnit.MILLISECONDS, valueSupplier, value -> {});
    }

    public Buffered(long lifeTime, TimeUnit timeUnit, SupplierWithException<T, E> valueSupplier) {
        this(lifeTime, timeUnit, valueSupplier, value -> {});
    }

    public Buffered(SupplierWithException<T, E> valueSupplier, ConsumerWithException<T, E> valueConsumer) {
        this(Long.MAX_VALUE, TimeUnit.MILLISECONDS, valueSupplier, valueConsumer);
    }

    public Buffered(long lifeTime, TimeUnit timeUnit, SupplierWithException<T, E> valueSupplier, ConsumerWithException<T, E> valueConsumer) {
        this.lifeTime = lifeTime;
        this.timeUnit = Objects.requireNonNull(timeUnit, () -> bundle.getString("common.util.Buffered.nullParameter.timeUnit"));
        this.valueSupplier = Objects.requireNonNull(valueSupplier, () -> bundle.getString("common.util.Buffered.nullParameter.valueSupplier"));
        this.valueConsumer = Objects.requireNonNull(valueConsumer, () -> bundle.getString("common.util.Buffered.nullParameter.valueConsumer"));
    }

    public void invalidate(T value) throws E {
        valueConsumer.accept(value);
        suppliedValue = null;
        previousSupplyTime = 0L;
    }

    public T value() throws E {
        long time = System.currentTimeMillis();

        if (time - previousSupplyTime > timeUnit.toMillis(lifeTime)) {
            suppliedValue = null;
            previousSupplyTime = time;
        }

        if (suppliedValue == null) {
            suppliedValue = valueSupplier.supply();
        }

        return suppliedValue;
    }

}
