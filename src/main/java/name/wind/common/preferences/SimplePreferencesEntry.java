package name.wind.common.preferences;

import java.util.function.Function;
import java.util.prefs.Preferences;

public class SimplePreferencesEntry<T> extends PreferencesEntry<String, T> {

    public SimplePreferencesEntry(Class<?> invoker, String name, Function<String, T> transformer, Function<T, String> reverseTransformer) {
        super(invoker, name, transformer, reverseTransformer);
    }

    @Override protected String loadRaw(Preferences node, String key) {
        return node.get(key, null);
    }

    @Override protected void saveRaw(Preferences node, String key, String string) {
        node.put(key, string);
    }

}
