package com.github.windchopper.common.preferences.entries;

import com.github.windchopper.common.preferences.PreferencesEntryFlatType;
import com.github.windchopper.common.preferences.PreferencesStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class CompositeEntryTest {

    @Mock private PreferencesStorage storage1st;
    @Mock private PreferencesStorage storage2nd;
    @Mock private PreferencesStorage childStorage1st;
    @Mock private PreferencesStorage childStorage2nd;

    @Test public void test() throws Exception {
        when(storage1st.child(eq("key"))).thenReturn(childStorage1st);
        when(storage2nd.child(eq("key"))).thenReturn(childStorage2nd);
        when(childStorage1st.value(eq("value"))).thenReturn("val1");
        when(childStorage1st.value(eq("timestamp"))).thenReturn(Instant.now().toString());

        var composition = new CompositeEntry.StorageComposition<>(
            Map.of("1", storage1st, "2", storage2nd))
                .loadFrom("1")
                .loadFrom("2")
                .propagateTo("2")
                .saveTo("1")
                .saveTo("2");

        var compositeEntry = new CompositeEntry<>(
            composition, "key", new PreferencesEntryFlatType<>(string -> string, string -> string));

        var value = compositeEntry.load();
        compositeEntry.save("val3");

        assertEquals("val1", value.getValue());

        verify(childStorage1st, times(1)).value(eq("value"));
        verify(childStorage1st, times(1)).value(eq("timestamp"));
        verify(childStorage2nd, times(2)).value(eq("value"));
        verify(childStorage2nd, times(2)).value(eq("timestamp"));
        verify(childStorage2nd, times(2)).saveValue(eq("timestamp"), anyString());
        verify(childStorage2nd, times(1)).saveValue(eq("value"), eq("val1"));
        verify(childStorage2nd, times(1)).saveValue(eq("value"), eq("val3"));
        verify(childStorage1st, times(1)).saveValue(eq("timestamp"), anyString());
        verify(childStorage1st, times(1)).saveValue(eq("value"), eq("val3"));
    }

}
