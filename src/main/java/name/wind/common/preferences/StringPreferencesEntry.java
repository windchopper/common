package name.wind.common.preferences;

public class StringPreferencesEntry extends SimplePreferencesEntry<String> {

    public StringPreferencesEntry(Class<?> invoker, String name) {
        super(invoker, name, string -> string, object -> object);
    }

}
