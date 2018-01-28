package com.github.windchopper.common.preferences;

import com.github.windchopper.common.preferences.types.FlatType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class) public class PreferencesEntryTest {

    @Mock private PreferencesStorage storage;

    @Before public void prepare() {
        when(storage.value(eq("stringKey"), any(String.class))).thenReturn("string");
        when(storage.value(eq("shortKey"), any(String.class))).thenReturn("1");
        when(storage.value(eq("booleanKey"), any(String.class))).thenReturn("true");
    }

    @Test public void test() {
        PreferencesEntry<String> stringEntry = new PreferencesEntry<>(storage, "stringKey", new FlatType<>(string -> string, string -> string), Duration.ZERO);
        PreferencesEntry<Short> shortEntry = new PreferencesEntry<>(storage, "shortKey", new FlatType<>(Short::parseShort, Object::toString), Duration.ZERO);
        PreferencesEntry<Boolean> booleanEntry = new PreferencesEntry<>(storage, "booleanKey", new FlatType<>(Boolean::parseBoolean, Object::toString), Duration.ZERO);

        assertEquals("string", stringEntry.get());
        assertEquals(Short.valueOf("1"), shortEntry.get());
        assertEquals(true, booleanEntry.get());

        verify(storage, times(3)).value(any(String.class), any(String.class));
    }

}
