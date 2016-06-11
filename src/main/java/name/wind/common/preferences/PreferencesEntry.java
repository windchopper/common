package name.wind.common.preferences;

import name.wind.common.util.Buffered;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public abstract class PreferencesEntry<S, T> implements Supplier<T>, Consumer<T> {

    private static final long DEFAULT__BUFFER_LIFETIME = 1L;
    private static final TimeUnit DEFAULT__BUFFER_LIFETIME_UNIT = TimeUnit.MINUTES;

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Logger logger = Logger.getLogger(
        PreferencesEntry.class.getName());

    private final Buffered<T> bufferedValue;

    public PreferencesEntry(Class<?> invoker, String name, Function<S, T> transformer, Function<T, S> reverseTransformer) {
        Objects.requireNonNull(name, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.name"));
        Objects.requireNonNull(transformer, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.transformer"));
        Objects.requireNonNull(reverseTransformer, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.reverseTransformer"));

        Preferences node = Preferences.userNodeForPackage(
            Objects.requireNonNull(invoker, () -> bundle.getString("common.preferences.PreferencesEntry.nullParameter.invoker")));

        bufferedValue = new Buffered<>(DEFAULT__BUFFER_LIFETIME, DEFAULT__BUFFER_LIFETIME_UNIT,
            () -> load(node, name, transformer), value -> save(node, name, value, reverseTransformer));
    }

    private T load(Preferences node, String name, Function<S, T> transformer) {
        T loaded = null;

        try {
            loaded = transformer.apply(loadRaw(node, name));
        } catch (Exception thrown) {
            logger.log(Level.WARNING, thrown, () -> bundle.getString("common.preferences.PreferencesEntry.fail.load"));
        }

        return loaded;
    }

    private void save(Preferences node, String name, T value, Function<T, S> reverseTransformer) {
        try {
            saveRaw(node, name, reverseTransformer.apply(value));
        } catch (Exception thrown) {
            logger.log(Level.WARNING, thrown, () -> bundle.getString("common.preferences.PreferencesEntry.fail.save"));
        }
    }

    protected abstract S loadRaw(Preferences node, String key) throws Exception;
    protected abstract void saveRaw(Preferences node, String key, S value) throws Exception;

    @Override public T get() {
        return bufferedValue.get();
    }

    @Override public void accept(T newValue) {
        bufferedValue.invalidate(newValue);
    }

}
