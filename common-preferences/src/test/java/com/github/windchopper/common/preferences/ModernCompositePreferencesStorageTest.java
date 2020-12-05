package com.github.windchopper.common.preferences;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class ModernCompositePreferencesStorageTest {

    @Mock private PreferencesStorage storageNode1st;
    @Mock private PreferencesStorage storageNode2nd;

    @Test public void testOrder() {
        when(storageNode1st.value(eq("key1"), any())).thenReturn("val1");
        when(storageNode2nd.value(eq("key2"), any())).thenReturn("val2");

        var compositeStorageNode = new ModernCompositePreferencesStorage<>(
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

        assertEquals("val1", compositeStorageNode.value("key1", null));
        assertEquals("val2", compositeStorageNode.value("key2", null));

        verify(storageNode2nd, never()).value(eq("key1"), any());
        verify(storageNode2nd, times(1)).putValue(eq("key1"), eq("val1"));
    }

}
