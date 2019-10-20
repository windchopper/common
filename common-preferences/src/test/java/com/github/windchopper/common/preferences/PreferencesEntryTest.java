package com.github.windchopper.common.preferences;

import com.github.windchopper.common.preferences.types.FlatType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class PreferencesEntryTest {

    @Mock private PreferencesStorage storage;

    @BeforeEach public void prepare() {
        when(storage.value(eq("stringKey"), nullable(String.class))).thenReturn("string");
        when(storage.value(eq("shortKey"), nullable(String.class))).thenReturn("1");
        when(storage.value(eq("booleanKey"), nullable(String.class))).thenReturn("true");
    }

    @Test public void test() {
        var stringEntry = new PreferencesEntry<String>(storage, "stringKey", new FlatType<>(string -> string, string -> string), Duration.ZERO);
        var shortEntry = new PreferencesEntry<Short>(storage, "shortKey", new FlatType<>(Short::parseShort, Object::toString), Duration.ZERO);
        var booleanEntry = new PreferencesEntry<Boolean>(storage, "booleanKey", new FlatType<>(Boolean::parseBoolean, Object::toString), Duration.ZERO);

        assertEquals("string", stringEntry.load());
        assertEquals(Short.valueOf("1"), shortEntry.load());
        assertEquals(true, booleanEntry.load());

        verify(storage, times(3)).value(any(String.class), nullable(String.class));
    }

}
