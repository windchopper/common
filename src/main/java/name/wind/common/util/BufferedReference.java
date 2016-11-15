package name.wind.common.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class BufferedReference<T> implements Supplier<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("name.wind.common.i18n.messages");

    private final Duration lifeTime;
    private final Supplier<T> valueSupplier;

    private Instant previousSupplyTime;
    private T suppliedValue;

    public BufferedReference(Duration lifeTime, Supplier<T> valueSupplier) {
        this.lifeTime = Objects.requireNonNull(lifeTime, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.lifeTime"));
        this.valueSupplier = Objects.requireNonNull(valueSupplier, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.valueSupplier"));

        invalidate();
    }

    public void invalidate() {
        previousSupplyTime = Instant.MIN;
        suppliedValue = null;
    }

    @Override public T get() {
        Instant now = Instant.now();

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
