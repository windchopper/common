package com.github.windchopper.common.preferences;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface PreferencesStorage {

    String SEPARATOR = "/";

    String value(String name) throws Exception;
    void saveValue(String name, String text) throws Exception;
    void dropValue(String name) throws Exception;

    PreferencesStorage child(String name) throws Exception;
    void dropChild(String name) throws Exception;

    Set<String> valueNames() throws Exception;
    Set<String> childNames() throws Exception;

    default Optional<Instant> timestamp() {
        return Optional.empty();
    }

}
