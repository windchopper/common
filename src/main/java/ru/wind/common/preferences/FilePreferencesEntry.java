package ru.wind.common.preferences;

import java.io.File;

public class FilePreferencesEntry extends SimplePreferencesEntry<File> {

    public FilePreferencesEntry(Class<?> invoker, String name) {
        super(invoker, name, File::new, Object::toString);
    }

}
