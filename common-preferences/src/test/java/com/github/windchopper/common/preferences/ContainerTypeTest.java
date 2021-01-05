package com.github.windchopper.common.preferences;

import com.github.windchopper.common.preferences.entries.StandardEntry;
import com.github.windchopper.common.preferences.types.StringType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class ContainerTypeTest {

    @Mock private PreferencesStorage storage;
    @Mock private PreferencesStorage childStorage;

    @BeforeEach void prepare() {
        reset(storage, childStorage);
    }

    @Test public void testMap() throws Exception {
        when(storage.child("map")).thenReturn(childStorage);

        var entry = new StandardEntry<>(storage, "map",
            new PreferencesEntryFlatMapType<String, Map<String, String>>(HashMap::new, new StringType()));

        var map = Map.of("1", "1", "2", "2", "3", "3");
        entry.save(map);

        verify(childStorage, times(1)).saveValue(eq("1"), eq("1"));
        verify(childStorage, times(1)).saveValue(eq("2"), eq("2"));
        verify(childStorage, times(1)).saveValue(eq("3"), eq("3"));

        when(childStorage.valueNames()).thenReturn(Set.of("1", "2", "3"));
        when(childStorage.value(eq("1"))).thenReturn("1");
        when(childStorage.value(eq("2"))).thenReturn("2");
        when(childStorage.value(eq("3"))).thenReturn("3");

        var loadedMap = entry.load().getValue();

        assertEquals(map, loadedMap);
        assertEquals(HashMap.class, loadedMap.getClass());
    }

    @Test public void testCollection() throws Exception {
        when(storage.child("collection")).thenReturn(childStorage);

        var entry = new StandardEntry<>(storage, "collection",
            new PreferencesEntryFlatCollectionType<String, List<String>>(ArrayList::new, new StringType()));

        var list = List.of("1", "2", "3");
        entry.save(list);

        verify(childStorage, times(1)).saveValue(eq("1"), eq("1"));
        verify(childStorage, times(1)).saveValue(eq("2"), eq("2"));
        verify(childStorage, times(1)).saveValue(eq("3"), eq("3"));
    }

    @Test public void testCollectionOrdering() throws Exception {
        when(storage.child("collection")).thenReturn(childStorage);
        when(childStorage.valueNames()).thenReturn(Set.of("1", "2", "3", "4"));
        when(childStorage.value(eq("1"))).thenReturn("1");
        when(childStorage.value(eq("2"))).thenReturn("2");
        when(childStorage.value(eq("3"))).thenReturn("3");
        when(childStorage.value(eq("4"))).thenReturn("3");

        var listEntry = new StandardEntry<>(storage, "collection",
            new PreferencesEntryFlatCollectionType<String, List<String>>(ArrayList::new, new StringType()));

        var list = List.of("1", "2", "3", "3");
        var loadedList = listEntry.load().getValue();

        assertEquals(list, loadedList);
        assertEquals(ArrayList.class, loadedList.getClass());

        var setEntry = new StandardEntry<>(storage, "collection",
            new PreferencesEntryFlatCollectionType<String, Set<String>>(HashSet::new, new StringType()));

        var set = Set.of("1", "2", "3");
        var loadedSet = setEntry.load().getValue();

        assertEquals(set, loadedSet);
        assertEquals(HashSet.class, loadedSet.getClass());
    }

}
