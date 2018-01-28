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
        Preferences preferences = Preferences.userRoot().node("com.github.windchopper.common.preferences/test");

        for (String childrenName : preferences.childrenNames()) {
            preferences.node(childrenName).removeNode();
        }

        for (String key : preferences.keys()) {
            preferences.remove(key);
        }

        storage = new PlatformPreferencesStorage(preferences);
    }

    @Test public void testFlatString() {
        PreferencesEntryType<String> stringType = new FlatType<>(string -> string, string -> string);
        PreferencesEntry<String> stringEntry = new PreferencesEntry<>(storage, "stringEntry", stringType, Duration.ZERO);
        String value = "testValue";
        assertNull(stringEntry.get());
        stringEntry.accept(value);
        assertEquals(value, stringEntry.get());
        assertEquals(value, storage.value("stringEntry", null));
        stringEntry.accept(null);
        assertEquals(null, stringEntry.get());
        assertEquals(null, storage.value("stringEntry", null));
    }

    @Test public void testFlatDouble() {
        PreferencesEntryType<Double> doubleType = new FlatType<>(Double::parseDouble, Object::toString);
        PreferencesEntry<Double> doubleEntry = new PreferencesEntry<>(storage, "doubleEntry", doubleType, Duration.ofMinutes(1));
        Double value = 1.1;
        assertNull(doubleEntry.get());
        doubleEntry.accept(value);
        assertEquals(value, doubleEntry.get());
        assertEquals(value.toString(), storage.value("doubleEntry", null));
        doubleEntry.accept(null);
        assertEquals(null, doubleEntry.get());
        assertEquals(null, storage.value("doubleEntry", null));
    }

    @Test public void testFlatCollection() {
        PreferencesEntryType<List<String>> stringListType = new FlatCollectionType<>(ArrayList::new, string -> string, string -> string);
        PreferencesEntry<List<String>> stringListEntry = new PreferencesEntry<>(storage, "stringListEntry", stringListType, Duration.ZERO);
        List<String> value = asList("1st", "2nd", "3rd", "4th");
        assertEquals(emptyList(), stringListEntry.get());
        assertTrue(stringListEntry.get() instanceof ArrayList);
        stringListEntry.accept(value);
        assertEquals(value, stringListEntry.get());
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
