package name.wind.common.preferences;

public class BooleanPreferencesEntry extends SimplePreferencesEntry<Boolean> {

    public BooleanPreferencesEntry(Class<?> invoker, String name) {
        super(invoker, name, Boolean::new, Object::toString);
    }

}
