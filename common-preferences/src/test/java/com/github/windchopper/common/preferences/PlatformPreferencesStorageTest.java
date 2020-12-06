package com.github.windchopper.common.preferences;

import com.github.windchopper.common.preferences.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.*;

public class PlatformPreferencesStorageTest {

    private final FlatType<String> stringType = new FlatType<>(identity(), identity());
    private final FlatType<Double> doubleType = new FlatType<>(Double::parseDouble, Object::toString);

    private final PreferencesEntryType<Set<String>> stringSetType = new FlatCollectionType<>(HashSet::new, identity(), identity());
    private final PreferencesEntryType<Map<String, String>> stringMapType = new FlatMapType<>(HashMap::new, identity(), identity());

    private PreferencesStorage storage;

    @BeforeEach public void prepare() throws BackingStoreException {
        var preferences = Preferences.userRoot().node("com.github.windchopper.common.preferences/test");

        for (var childrenName : preferences.childrenNames()) {
            preferences.node(childrenName).removeNode();
        }

        for (var key : preferences.keys()) {
            preferences.remove(key);
        }

        storage = new PlatformPreferencesStorage(preferences);
    }

    @Test public void testFlatString() {
        var stringEntry = new PreferencesEntry<>(storage, "stringEntry", stringType);
        assertNull(stringEntry.load());

        var stringValue = "testValue";

        stringEntry.save(stringValue);
        assertEquals(stringValue, stringEntry.load());

        var stringEntryText = storage.value("stringEntry");
        assertTrue(stringEntryText.isPresent());
        assertEquals(stringValue, stringEntryText.get().text());

        stringEntry.save(null);
        assertNull(stringEntry.load());

        stringEntryText = storage.value("stringEntry");
        assertTrue(stringEntryText.isPresent());
        assertNull(stringEntryText.get().text());
    }

    @Test public void testFlatDouble() {
        var doubleEntry = new PreferencesEntry<>(storage, "doubleEntry", doubleType);
        assertNull(doubleEntry.load());

        var doubleValue = Double.valueOf(1.1);

        doubleEntry.save(doubleValue);
        assertEquals(doubleValue, doubleEntry.load());

        var doubleEntryText = storage.value("doubleEntry");
        assertTrue(doubleEntryText.isPresent());
        assertEquals(doubleValue.toString(), doubleEntryText.get().text());

        doubleEntry.save(null);
        assertNull(doubleEntry.load());

        doubleEntryText = storage.value("doubleEntry");
        assertTrue(doubleEntryText.isPresent());
        assertNull(doubleEntryText.get().text());
    }

    @Test public void testFlatCollection() {
        var stringSetEntry = new PreferencesEntry<>(storage, "stringSetEntry", stringSetType);
        assertEquals(emptySet(), stringSetEntry.load());

        var stringSet = Set.of("1st", "2nd", "3rd", "4th");

        stringSetEntry.save(stringSet);
        assertEquals(stringSet, stringSetEntry.load());
    }

    @Test public void testFlatMap() {
        var stringMapEntry = new PreferencesEntry<>(storage, "stringMapEntry", stringMapType);
        assertEquals(emptyMap(), stringMapEntry.load());

        var stringMap = Map.of("1", "1st", "2", "2nd", "3", "3rd", "4", "4th");

        stringMapEntry.save(stringMap);
        assertEquals(stringMap, stringMapEntry.load());
    }

}
