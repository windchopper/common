package com.github.windchopper.common.preferences;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) public class CompositePreferencesStorageTest {

    @Mock private PreferencesStorage storageNode1st;
    @Mock private PreferencesStorage storageNode2nd;

    @Test public void testLoadValue() {
        when(storageNode1st.value(eq("key#1"), nullable(String.class))).thenReturn("value#1@1");
        when(storageNode1st.value(eq("key#2"), nullable(String.class))).thenReturn(null);
        when(storageNode1st.value(eq("key#3"), nullable(String.class))).thenReturn("value#3@1");
        when(storageNode2nd.value(eq("key#2"), nullable(String.class))).thenReturn("value#2@2");

        var compositeStorageNode = new CompositePreferencesStorage(List.of(
            new CompositePreferencesStorage.Mediator(storageNode1st, 1, 2, false),
            new CompositePreferencesStorage.Mediator(storageNode2nd, 2, 1, false)));

        assertEquals("value#1@1", compositeStorageNode.value("key#1", null));
        assertEquals("value#2@2", compositeStorageNode.value("key#2", null));
        assertEquals("value#3@1", compositeStorageNode.value("key#3", null));

        assertEquals("value#nonexistent", compositeStorageNode.value("key#nonexistent", "value#nonexistent"));
    }

    @Test public void testSaveValue() {
        var compositeStorageNode = new CompositePreferencesStorage(List.of(
            new CompositePreferencesStorage.Mediator(storageNode1st, 1, 2, false),
            new CompositePreferencesStorage.Mediator(storageNode2nd, 2, 1, false)));

        compositeStorageNode.putValue("key#new", "value#new");

        var order = inOrder(storageNode2nd, storageNode1st);

        order.verify(storageNode2nd, times(1)).putValue(eq("key#new"), eq("value#new"));
        order.verify(storageNode1st, times(1)).putValue(eq("key#new"), eq("value#new"));

        compositeStorageNode.removeValue("key#new");

        order.verify(storageNode2nd, times(1)).removeValue(eq("key#new"));
        order.verify(storageNode1st, times(1)).removeValue(eq("key#new"));
    }

}