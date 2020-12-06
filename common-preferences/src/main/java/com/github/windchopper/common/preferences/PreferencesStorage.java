package com.github.windchopper.common.preferences;

import java.util.Optional;
import java.util.Set;

public interface PreferencesStorage {

    Optional<PreferencesEntryText> value(String name) throws PreferencesException;
    void saveValue(String name, String text) throws PreferencesException;
    void removeValue(String name) throws PreferencesException;

    PreferencesStorage child(String name) throws PreferencesException;
    void removeChild(String name) throws PreferencesException;

    Set<String> valueNames() throws PreferencesException;
    Set<String> childNames() throws PreferencesException;

}
