package name.wind.common.preferences;

public class DoublePreferencesEntry extends SimplePreferencesEntry<Double> {

    public DoublePreferencesEntry(Class<?> invoker, String name) {
        super(invoker, name, Double::new, Object::toString);
    }

}
