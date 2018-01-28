package com.github.windchopper.common.preferences;

import java.util.Set;

public interface PreferencesStorage {

    String PATH_NAME_SEPARATOR = "/";

    String value(String name, String defaultValue);

    void putValue(String name, String value);
    void removeValue(String name);

    Set<String> valueNames();
    Set<String> childNames();

    PreferencesStorage child(String name);

    void removeChild(String name);

}
