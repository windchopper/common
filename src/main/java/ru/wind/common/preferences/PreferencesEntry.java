package ru.wind.common.preferences;

import ru.wind.common.util.Buffered;
import ru.wind.common.util.function.Consumer;
import ru.wind.common.util.function.Supplier;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.prefs.Preferences;

public abstract class PreferencesEntry<S, T> implements Supplier<T, PreferencesException>, Consumer<T, PreferencesException> {

    private static final long DEFAULT__BUFFER_LIFETIME = 1L;
    private static final TimeUnit DEFAULT__BUFFER_LIFETIME_UNIT = TimeUnit.MINUTES;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");

    private final Buffered<T, PreferencesException> bufferedValue;

    public PreferencesEntry(Class<?> invoker, String name, Function<S, T> transformer, Function<T, S> reverseTransformer) {
        Objects.requireNonNull(name, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.name"));
        Objects.requireNonNull(transformer, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.transformer"));
        Objects.requireNonNull(reverseTransformer, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.reverseTransformer"));

        Preferences node = Preferences.userNodeForPackage(
            Objects.requireNonNull(invoker, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.invoker")));

        bufferedValue = new Buffered<>(
            DEFAULT__BUFFER_LIFETIME,
            DEFAULT__BUFFER_LIFETIME_UNIT,
            () -> transformer.apply(loadRaw(node, name)),
            value -> saveRaw(node, name, reverseTransformer.apply(value))
        );
    }

    protected abstract S loadRaw(Preferences node, String key) throws PreferencesException;
    protected abstract void saveRaw(Preferences node, String key, S value) throws PreferencesException;

    @Override public T supply() throws PreferencesException {
        return bufferedValue.value();
    }

    @Override public void accept(T newValue) throws PreferencesException {
        bufferedValue.invalidate(newValue);
    }

}
