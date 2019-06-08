package com.github.windchopper.common.preferences;

import com.github.windchopper.common.preferences.types.FlatCollectionType;
import com.github.windchopper.common.preferences.types.FlatMapType;
import com.github.windchopper.common.preferences.types.FlatType;
import com.github.windchopper.common.util.Pipeliner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.*;

@RunWith(JUnit4.class) public class PlatformPreferencesStorageTest {

    private PreferencesStorage storage;

    @Before public void prepare() throws BackingStoreException {
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
        assertNull(stringEntry.get());
        stringEntry.accept(value);
        assertEquals(value, stringEntry.get());
        assertEquals(value, storage.value("stringEntry", null));
        stringEntry.accept(null);
        assertNull(stringEntry.get());
        assertNull(storage.value("stringEntry", null));
    }

    @Test public void testFlatDouble() {
        var doubleType = new FlatType<>(Double::parseDouble, Object::toString);
        var doubleEntry = new PreferencesEntry<>(storage, "doubleEntry", doubleType, Duration.ofMinutes(1));
        Double value = 1.1;
        assertNull(doubleEntry.get());
        doubleEntry.accept(value);
        assertEquals(value, doubleEntry.get());
        assertEquals(value.toString(), storage.value("doubleEntry", null));
        doubleEntry.accept(null);
        assertNull(doubleEntry.get());
        assertNull(storage.value("doubleEntry", null));
    }

    @Test public void testFlatCollection() {
        PreferencesEntryType<List<String>> stringListType = new FlatCollectionType<>(ArrayList::new, string -> string, string -> string);
        PreferencesEntry<List<String>> stringListEntry = new PreferencesEntry<>(storage, "stringListEntry", stringListType, Duration.ZERO);
        List<String> value = asList("1st", "2nd", "3rd", "4th");
        assertEquals(emptyList(), stringListEntry.get());
        assertTrue(stringListEntry.get() instanceof ArrayList);
        stringListEntry.accept(value);
        assertTrue(stringListEntry.get().contains("1st"));
        assertTrue(stringListEntry.get().contains("2nd"));
        assertTrue(stringListEntry.get().contains("3rd"));
        assertTrue(stringListEntry.get().contains("4th"));
        assertTrue(stringListEntry.get() instanceof ArrayList);
        assertEquals(value.size(), storage.child("stringListEntry").valueNames().size());
    }

    @Test public void testFlatMap() {
        PreferencesEntryType<Map<String, String>> stringMapType = new FlatMapType<>(HashMap::new, string -> string, string -> string);
        PreferencesEntry<Map<String, String>> stringMapEntry = new PreferencesEntry<>(storage, "stringMapEntry", stringMapType, Duration.ZERO);
        Map<String, String> value = Pipeliner.of(HashMap<String, String>::new)
            .set(map -> entryValue -> map.put("1", entryValue), "1st")
            .set(map -> entryValue -> map.put("2", entryValue), "2nd")
            .set(map -> entryValue -> map.put("3", entryValue), "3rd")
            .set(map -> entryValue -> map.put("4", entryValue), "4th")
            .get();
        assertEquals(emptyMap(), stringMapEntry.get());
        assertTrue(stringMapEntry.get() instanceof HashMap);
        stringMapEntry.accept(value);
        assertEquals(value, stringMapEntry.get());
        assertTrue(stringMapEntry.get() instanceof HashMap);
        assertEquals(value.size(), storage.child("stringMapEntry").valueNames().size());
    }

}
