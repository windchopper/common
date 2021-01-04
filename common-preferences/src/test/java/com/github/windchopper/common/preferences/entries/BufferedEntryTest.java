package com.github.windchopper.common.preferences.entries;

import com.github.windchopper.common.preferences.PreferencesStorage;
import com.github.windchopper.common.preferences.types.StringType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class BufferedEntryTest {

    @Mock private PreferencesStorage storage;
    @Mock private PreferencesStorage childStorage;

    @Test public void test() throws Exception {
        when(storage.child(eq("key"))).thenReturn(childStorage);
        when(childStorage.value(eq("value"))).thenReturn("val");

        var entry = new BufferedEntry<>(ChronoUnit.FOREVER.getDuration(), new StandardEntry<>(
            storage, "key", new StringType()));

        entry.load();
        entry.load();

        entry.save("valChanged");

        entry.load();
        entry.load();

        verify(childStorage, times(2)).saveValue(anyString(), anyString());
        verify(childStorage, times(4)).value(anyString());
    }

}
