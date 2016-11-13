package name.wind.common.util;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BufferedReference<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("name.wind.common.i18n.messages");

    private final Duration lifeTime;
    private final Supplier<T> valueSupplier;
    private final Consumer<T> valueModifier;

    private Temporal previousSupplyTime;
    private T suppliedValue;

    public BufferedReference(Duration lifeTime, Supplier<T> valueSupplier) {
        this(lifeTime, valueSupplier, value -> {});
    }

    public BufferedReference(Duration lifeTime, Supplier<T> valueSupplier, Consumer<T> valueModifier) {
        this.lifeTime = Objects.requireNonNull(lifeTime, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.lifeTime"));
        this.valueSupplier = Objects.requireNonNull(valueSupplier, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.valueSupplier"));
        this.valueModifier = Objects.requireNonNull(valueModifier, () -> bundle.getString("name.wind.common.util.Buffered.nullParameter.valueModifier"));
    }

    public T supply() {
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

    public void modify(T value) {
        valueModifier.accept(value);
        previousSupplyTime = Instant.MIN;
        suppliedValue = null;
    }

}
