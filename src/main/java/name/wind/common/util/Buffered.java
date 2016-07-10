package name.wind.common.util;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Buffered<T> implements Supplier<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("name.wind.common.i18n.messages");

    private final long lifeTime;
    private final TimeUnit timeUnit;
    private final Supplier<T> valueSupplier;
    private final Consumer<T> valueConsumer;

    private long previousSupplyTime;
    private T suppliedValue;

    public Buffered(Supplier<T> valueSupplier) {
        this(Long.MAX_VALUE, TimeUnit.MILLISECONDS, valueSupplier, value -> {});
    }

    public Buffered(long lifeTime, TimeUnit timeUnit, Supplier<T> valueSupplier) {
        this(lifeTime, timeUnit, valueSupplier, value -> {});
    }

    public Buffered(Supplier<T> valueSupplier, Consumer<T> valueConsumer) {
        this(Long.MAX_VALUE, TimeUnit.MILLISECONDS, valueSupplier, valueConsumer);
    }

    public Buffered(long lifeTime, TimeUnit timeUnit, Supplier<T> valueSupplier, Consumer<T> valueConsumer) {
        this.lifeTime = lifeTime;
        this.timeUnit = Objects.requireNonNull(timeUnit, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.timeUnit"));
        this.valueSupplier = Objects.requireNonNull(valueSupplier, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.valueSupplier"));
        this.valueConsumer = Objects.requireNonNull(valueConsumer, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.valueConsumer"));
    }

    public void invalidate(T value) {
        valueConsumer.accept(value);
        suppliedValue = null;
        previousSupplyTime = 0L;
    }

    @Override public T get() {
        long time = System.currentTimeMillis();

        if (time - previousSupplyTime > timeUnit.toMillis(lifeTime)) {
            suppliedValue = null;
        }

        if (suppliedValue == null) {
            suppliedValue = valueSupplier.get();
            previousSupplyTime = time;
        }

        return suppliedValue;
    }

}
