package com.github.windchopper.common.preferences;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class CompositePreferencesStorageTest {

    @Mock private PreferencesStorage storageNode1st;
    @Mock private PreferencesStorage storageNode2nd;

    @Test public void testOrder() {
        when(storageNode1st.value(eq("key1"))).thenReturn(Optional.of(new PreferencesEntryText("val1")));
        when(storageNode1st.value(eq("key2"))).thenReturn(Optional.empty());

        when(storageNode2nd.value(eq("key1"))).thenReturn(Optional.empty());
        when(storageNode2nd.value(eq("key2"))).thenReturn(Optional.of(new PreferencesEntryText("val2")));

        var compositeStorageNode = new CompositePreferencesStorage<>(
            Map.of("1", storageNode1st, "2", storageNode2nd))
                .onLoad()
                .tryStorage("1")
                .tryStorage("2")
                .propagateToStorage("2")
                .enough()
                .onSave()
                .saveToStorage("1")
                .saveToStorage("2")
                .enough();

        var value1st = compositeStorageNode.value("key1");
        var value2nd = compositeStorageNode.value("key2");

        assertTrue(value1st.isPresent());
        assertEquals("val1", value1st.get().text());
        assertTrue(value2nd.isPresent());
        assertEquals("val2", value2nd.get().text());

        verify(storageNode2nd, times(1)).value(eq("key1"));
        verify(storageNode2nd, times(1)).saveValue(eq("key1"), eq("val1"));
    }

}
