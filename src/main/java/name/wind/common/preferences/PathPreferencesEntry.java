package name.wind.common.preferences;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathPreferencesEntry extends SimplePreferencesEntry<Path> {

    public PathPreferencesEntry(Class<?> invoker, String name) {
        super(invoker, name, Paths::get, Object::toString);
    }

}
