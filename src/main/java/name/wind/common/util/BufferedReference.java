package name.wind.common.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class BufferedReference<T> implements Supplier<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("name.wind.common.i18n.messages");

    private static final String BUNDLE_KEY__NULL_PARAMETER = "name.wind.common.nullParameter";

    private final Duration lifeTime;
    private final Supplier<T> valueSupplier;

    private Instant previousSupplyTime = Instant.MIN;
    private T suppliedValue;

    public BufferedReference(@Nonnull Duration lifeTime, @Nonnull Supplier<T> valueSupplier) {
        this.lifeTime = requireNonNull(lifeTime, () -> String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "lifeTime"));
        this.valueSupplier = requireNonNull(valueSupplier, () -> String.format(bundle.getString(BUNDLE_KEY__NULL_PARAMETER), "valueSupplier"));
    }

    public void invalidate() {
        previousSupplyTime = Instant.MIN;
        suppliedValue = null;
    }

    @Override public @Nullable T get() {
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
