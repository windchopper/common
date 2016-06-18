package name.wind.common.preferences;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class StructuredPreferencesEntry<T> extends PreferencesEntry<StructuredPreferencesEntry.StructuredValue, T>  {

    public static class StructuredValue extends HashMap<String, String> {

        private final String name;
        private final Set<StructuredValue> children = new HashSet<>(0);

        public StructuredValue(String name) {
            this.name = name;
        }

        public String name() {
            return name;
        }

        public Set<StructuredValue> children() {
            return children;
        }

        private StructuredValue load(Preferences preferences) throws BackingStoreException {
            for (String key : preferences.keys()) {
                put(key, preferences.get(key, null));
            }

            for (String childName : preferences.childrenNames()) {
                children.add(new StructuredValue(childName).load(preferences.node(childName)));
            }

            return this;
        }

        @SuppressWarnings("Convert2streamapi") private StructuredValue save(Preferences preferences) throws BackingStoreException {
            Preferences node = preferences.node(name);

            for (String key : node.keys()) {
                node.remove(key);
            }

            for (String childName : node.childrenNames()) {
                node.node(childName).removeNode();
            }

            for (Map.Entry<String, String> entry : entrySet()) {
                if (entry.getValue() != null) {
                    node.put(entry.getKey(), entry.getValue());
                }
            }

            for (StructuredValue child : children) {
                child.save(node);
            }

            return this;
        }

    }

    public StructuredPreferencesEntry(Class<?> invoker, String name, Function<StructuredValue, T> transformer, Function<T, StructuredValue> reverseTransformer) {
        super(invoker, name, transformer, reverseTransformer);
    }

    @Override protected StructuredValue loadRaw(Preferences preferences, String key) throws BackingStoreException {
        return new StructuredValue(key).load(preferences.node(key));
    }

    @Override protected void saveRaw(Preferences preferences, String key, StructuredValue structuredValue) throws BackingStoreException {
        structuredValue.save(preferences);
    }

}