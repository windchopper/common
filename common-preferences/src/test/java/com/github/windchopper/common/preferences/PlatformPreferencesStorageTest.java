package com.github.windchopper.common.preferences;

import com.github.windchopper.common.preferences.types.FlatCollectionType;
import com.github.windchopper.common.preferences.types.FlatMapType;
import com.github.windchopper.common.preferences.types.FlatType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;

public class PlatformPreferencesStorageTest {

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
        var stringType = new FlatType<>(string -> string, string -> string);
        var stringEntry = new PreferencesEntry<>(storage, "stringEntry", stringType, Duration.ZERO);
        var value = "testValue";
        assertNull(stringEntry.load());
        stringEntry.save(value);
        assertEquals(value, stringEntry.load());
        assertEquals(value, storage.value("stringEntry", null));
        stringEntry.save(null);
        assertNull(stringEntry.load());
        assertNull(storage.value("stringEntry", null));
    }

    @Test public void testFlatDouble() {
        var doubleType = new FlatType<>(Double::parseDouble, Object::toString);
        var doubleEntry = new PreferencesEntry<>(storage, "doubleEntry", doubleType, Duration.ofMinutes(1));
        Double value = 1.1;
        assertNull(doubleEntry.load());
        doubleEntry.save(value);
        assertEquals(value, doubleEntry.load());
        assertEquals(value.toString(), storage.value("doubleEntry", null));
        doubleEntry.save(null);
        assertNull(doubleEntry.load());
        assertNull(storage.value("doubleEntry", null));
    }

    @Test public void testFlatCollection() {
        PreferencesEntryType<List<String>> stringListType = new FlatCollectionType<>(ArrayList::new, string -> string, string -> string);
        PreferencesEntry<List<String>> stringListEntry = new PreferencesEntry<>(storage, "stringListEntry", stringListType, Duration.ZERO);
        List<String> value = List.of("1st", "2nd", "3rd", "4th");
        assertEquals(List.of(), stringListEntry.load());
        stringListEntry.save(value);
        assertTrue(stringListEntry.load().contains("1st"));
        assertTrue(stringListEntry.load().contains("2nd"));
        assertTrue(stringListEntry.load().contains("3rd"));
        assertTrue(stringListEntry.load().contains("4th"));
        assertEquals(value.size(), storage.child("stringListEntry").valueNames().size());
    }

    @Test public void testFlatMap() {
        PreferencesEntryType<Map<String, String>> stringMapType = new FlatMapType<>(HashMap::new, Function.identity(), Function.identity());
        PreferencesEntry<Map<String, String>> stringMapEntry = new PreferencesEntry<>(storage, "stringMapEntry", stringMapType, Duration.ZERO);
        Map<String, String> stringMap = Map.of(
            "1", "1st",
            "2", "2nd",
            "3", "3rd",
            "4", "4th");
        assertEquals(Map.of(), stringMapEntry.load());
        stringMapEntry.save(stringMap);
        assertEquals(stringMap, stringMapEntry.load());
        assertEquals(stringMap.size(), storage.child("stringMapEntry").valueNames().size());
    }

}
